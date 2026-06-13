# ARQUITETURA E POLÍTICA DE BACKUP DE SEGURANÇA (SPRING BOOT & POSTGRESQL)

Este documento define a estratégia técnica para salvaguarda de dados em ambientes produtivos utilizando o ecossistema Java Spring Boot 3.x e PostgreSQL. A arquitetura aqui descrita prioriza a resiliência, a eficiência de recursos do sistema e a conformidade com padrões de retenção de dados corporativos.

---

## 1. NÍVEL 1: FUNDAMENTOS E ARQUITETURA DE DELEGAÇÃO

### 1.1 O Antipadrão da Exportação via Camada de Aplicação
O uso de Java/JPA (Hibernate) para realizar backups através da leitura de registros via `EntityManager` ou `Repositories` é estritamente proibido em ambientes de alta disponibilidade. 
*   **Consumo de Memória:** O carregamento de milhões de registros na Heap da JVM causa picos de *Garbage Collection* e riscos de `OutOfMemoryError`.
*   **Degradação de Throughput:** O tráfego de dados massivo entre o DB e a aplicação satura o pool de conexões e a largura de banda da rede.
*   **Falta de Consistência:** Diferente do `pg_dump`, uma exportação via JPA não garante um *snapshot* transacional consistente sem bloquear as tabelas manualmente.

### 1.2 Arquitetura de Delegação (Orquestrador vs. Motor)
A solução adota a **Delegação de Operação**:
1.  **Spring Boot (Orquestrador):** Gerencia o tempo (`@Scheduled`), logs, monitoramento e o disparo do processo.
2.  **PostgreSQL (Motor):** Utiliza o binário nativo `pg_dump` para realizar o trabalho pesado de IO e serialização de dados no SO.

### 1.3 Implementação Mínima Viável (MVP)

```java
package br.com.aliancapsicossocial.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class BasicBackupService {

    @Value("${spring.datasource.url}")
    private String dbUrl; // Ex: jdbc:postgresql://localhost:5432/db_name

    @Value("${app.backup.directory:/var/backups/db}")
    private String backupDir;

    @Scheduled(cron = "0 0 1 * * *") // Diário às 01:00 AM
    public void runBasicBackup() {
        String dbName = dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File outputFile = new File(backupDir, dbName + "_" + timestamp + ".dump");

        ProcessBuilder pb = new ProcessBuilder(
            "pg_dump",
            "-h", "localhost",
            "-U", "postgres",
            "-F", "c", // Formato Customizado (Binário Compactado)
            "-f", outputFile.getAbsolutePath(),
            dbName
        );

        try {
            log.info("Iniciando backup básico em: {}", outputFile.getName());
            Process process = pb.start();
            int exitCode = process.waitFor();
            log.info("Backup finalizado com código: {}", exitCode);
        } catch (IOException | InterruptedException e) {
            log.error("Falha crítica no backup", e);
            Thread.currentThread().interrupt();
        }
    }
}
```

---

## 2. NÍVEL 2: GERENCIAMENTO DE CICLO DE VIDA E RETENÇÃO (GFS)

### 2.1 Matriz de Agendamento GFS (Grandfather-Father-Son)
Para evitar sobrecarga de I/O, as janelas de execução são alternadas entre a madrugada e o início da manhã.

| Ciclo | Periodicidade | Expressão Cron | Horário | Retenção |
| :--- | :--- | :--- | :--- | :--- |
| **Son** | Diário | `0 0 1 * * *` | 01:00 | 7 Dias |
| **Father** | Semanal | `0 0 2 * * SUN` | 02:00 | 4 Semanas |
| **Father** | Quinzenal | `0 0 3 1,15 * *` | 03:00 | 2 Meses |
| **Grandfather**| Mensal | `0 0 4 1 * *` | 04:00 | 12 Meses |
| **Grandfather**| Trimestral | `0 30 4 1 1,4,7,10 *`| 04:30 | 2 Anos |
| **Grandfather**| Semestral | `0 0 5 1 1,7 *` | 05:00 | 3 Anos |
| **Legal** | Anual | `0 0 6 1 1 *` | 06:00 | Definitivo |

### 2.2 Segurança de Credenciais e Rotação de Arquivos
O uso de senhas em linha de comando é inseguro. Utilizamos a injeção via `environment` do processo e implementamos a lógica de expurgo local baseada em idade.

```java
@Service
@Slf4j
public class RetentionBackupService {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public void executeSecureBackup(String dbName, String path, long retentionDays) {
        ProcessBuilder pb = new ProcessBuilder("pg_dump", "-U", username, "-F", "c", "-f", path, dbName);
        
        // Segurança: Passagem de senha via variável de ambiente do processo filho
        pb.environment().put("PGPASSWORD", password);

        try {
            Process process = pb.start();
            if (process.waitFor() == 0) {
                applyRotationPolicy(new File(path).getParentFile(), retentionDays);
            }
        } catch (Exception e) {
            log.error("Erro no processo de backup/rotação", e);
        }
    }

    private void applyRotationPolicy(File directory, long days) {
        File[] files = directory.listFiles();
        if (files == null) return;

        long threshold = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L);
        for (File file : files) {
            if (file.lastModified() < threshold) {
                if (file.delete()) log.info("Arquivo expirado removido: {}", file.getName());
            }
        }
    }
}
```

---

## 3. NÍVEL 3: RESILIÊNCIA ENTERPRISE E ALTA DISPONIBILIDADE

### 3.1 Estratégia Cloud Storage (Offsite) e Ciclo de Vida
Backups locais são vulneráveis a falhas de hardware no servidor de banco de dados. A estratégia **3-2-1** exige que os dumps sejam enviados imediatamente para um Storage de Objetos (Ex: AWS S3).

*   **Custom Format (-F c):** Escolhido por suportar compressão nativa *zlib*, permitir restauração paralela (`pg_restore -j`) e possibilitar a seleção de objetos específicos durante o restore.
*   **S3 Lifecycle Policy:** 
    *   **Standard:** Backups Diários/Semanais (Acesso rápido).
    *   **Glacier Instant Retrieval:** Backups Mensais (Transição após 30 dias).
    *   **Glacier Deep Archive:** Backups Anuais (Retenção legal de longo prazo, custo mínimo).

### 3.2 Tolerância a Falhas e Telemetria
Implementação avançada com captura de erros, observabilidade via Micrometer (Prometheus) e integração de logs.

```java
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class EnterpriseBackupExecutor {

    private final MeterRegistry meterRegistry;

    public EnterpriseBackupExecutor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void executeWithTelemetry(String dbName, String targetPath) {
        Timer.Sample sample = Timer.start(meterRegistry);
        ProcessBuilder pb = new ProcessBuilder("pg_dump", "-F", "c", "-v", "-f", targetPath, dbName);
        
        try {
            Process process = pb.start();

            // Captura de erro em tempo real para o Log da Aplicação
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                reader.lines().forEach(line -> log.warn("[pg_dump-stderr] {}", line));
            }

            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                sample.stop(meterRegistry.timer("db.backup.duration", "status", "success"));
                meterRegistry.counter("db.backup.total", "status", "success").increment();
                // Chamar S3Service.upload(targetPath) aqui
            } else {
                sample.stop(meterRegistry.timer("db.backup.duration", "status", "error"));
                meterRegistry.counter("db.backup.total", "status", "error").increment();
                triggerAlert("Backup falhou com código " + exitCode);
            }
        } catch (Exception e) {
            log.error("Erro catastrófico no backup enterprise", e);
            meterRegistry.counter("db.backup.total", "status", "exception").increment();
        }
    }

    private void triggerAlert(String message) {
        // Integração com Webhooks (Slack/Teams) ou PagerDuty
        log.error("ALERT: {}", message);
    }
}
```

### 3.3 Verificação de Integridade (Métrica de Sucesso)
Um backup só existe após ser testado. Recomenda-se um Job trimestral que:
1.  Baixa o último dump do S3.
2.  Executa `pg_restore --list` para verificar a estrutura do cabeçalho binário.
3.  Restaura em uma instância efêmera de Docker para validar o esquema.
