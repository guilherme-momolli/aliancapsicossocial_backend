# 🗄️ Boas Práticas e Padrões para Migrações de Banco de Dados com Flyway

Este documento define os padrões e boas práticas para gerenciamento de esquema e migrações de banco de dados no **Aliança Psicossocial** utilizando a ferramenta **Flyway**.

---

## 📁 1. Localização e Organização dos Arquivos

Todas as migrações SQL devem ser salvas no diretório padrão do Spring Boot/Flyway:
`src/main/resources/db/migration/`

---

## 🏷️ 2. Padrão de Nomenclatura das Migrações

O Flyway utiliza prefixos e padrões específicos no nome do arquivo para entender a ordem e a natureza da execução:

### A. Migrações Versionadas (Executadas uma única vez)
* **Padrão:** `V<VERSÃO>__<descrição_curta_com_underlines>.sql`
* **Exemplos:**
  * `V1__criar_tabelas_usuarios_e_perfis.sql`
  * `V2__adicionar_campo_tipo_contrato_vagas.sql`
* **Regras para a Versão:**
  * Use números inteiros sequenciais simples (`V1`, `V2`, `V3`, etc.) ou datas/carimbos de data e hora (`V20260605180000__...`) caso múltiplas frentes trabalhem em paralelo para evitar colisões de números. No Aliança Psicossocial, preferiremos o sequencial simples (`V1`, `V2`, ...) enquanto o time de desenvolvimento for pequeno.
  * > [!IMPORTANT]
    > O separador entre o número da versão e a descrição é constituído de **dois underscores (`__`)**. Um único underscore causará erro de parse do Flyway.

### B. Migrações Repetíveis (Executadas toda vez que o arquivo sofrer modificações)
* Usadas para views, procedures, triggers e funções que podem ser recriadas.
* **Padrão:** `R__<descrição>.sql` (ex: `R__criar_view_candidatos_ativos.sql`).

---

## 🔒 3. Imutabilidade das Migrações

* > [!WARNING]
  > **Nunca modifique uma migração que já foi executada/commitada.**
  > O Flyway gera um *checksum* (hash MD5) de cada arquivo SQL executado e salva na tabela interna `flyway_schema_history`. Se você alterar um arquivo existente, o checksum falhará na próxima inicialização da aplicação, impedindo que ela suba.
* **Como corrigir um erro no banco?**
  * Se o erro foi identificado antes do commit para branches compartilhadas (localmente): você pode rodar um comando para limpar/limpar a tabela e recomeçar (ex: `docker compose down -v` para limpar os volumes do banco).
  * Se o erro já foi commitado/enviado para staging/produção: **crie uma nova migração versionada** (ex: `V3__corrigir_tipo_coluna.sql`) que realiza as devidas alterações via comandos SQL (`ALTER TABLE`, `DROP COLUMN`, etc.).

---

## 📐 4. Padrões SQL para PostgreSQL no Projeto

* **Identificadores (UUIDs):**
  * Para chaves primárias (PK), utilize preferencialmente o tipo `UUID`.
  * No PostgreSQL, use o tipo nativo `UUID` e a função de geração automática:
    ```sql
    CREATE TABLE usuarios (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        email VARCHAR(255) NOT NULL UNIQUE,
        ...
    );
    ```
* **Nomes de Tabelas e Colunas:**
  * Sempre em letras minúsculas (snake_case) e no plural para tabelas: `usuarios`, `perfis_candidatos`, `vagas`, `candidaturas`.
  * Nomes de colunas no singular: `data_criacao`, `tipo_pessoa`.
* **Segurança e Rollback:**
  * Evite migrações complexas com múltiplos comandos gigantescos. Divida em migrações menores e lógicas para isolar erros de sintaxe ou de integridade.

---

## 🚀 5. Execução em Múltiplos Ambientes

* **Desenvolvimento Local:**
  * O Flyway roda automaticamente na inicialização da aplicação Spring Boot (`./mvnw spring-boot:run` ou via container Docker).
* **Produção / Homologação:**
  * O Flyway garante que o banco de dados de produção receba apenas as alterações pendentes de forma incremental, prevenindo perda de dados ou inconsistências de ambientes.
  * Evite ativar a propriedade `spring.flyway.clean-on-validation-error` em produção para evitar a deleção acidental de dados da base.
