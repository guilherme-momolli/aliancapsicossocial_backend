# ☕ Boas Práticas Java 21 e Código Limpo

Este documento define os padrões de desenvolvimento e escrita de código em **Java 21** no projeto **Aliança Psicossocial**.

---

## ⚡ Recursos Modernos do Java 21

### 1. Java Records
Utilize `record` para classes imutáveis que apenas transportam dados (DTOs/VOs). Evita boilerplate e garante a imutabilidade por padrão.
```java
// DTO de resposta imutável
public record VagaResponseDTO(
    UUID id,
    String titulo,
    BigDecimal salario,
    String tipoContrato
) {}
```

### 2. Inferência de Tipos locais com `var`
Use `var` para variáveis locais onde o tipo seja óbvio a partir da atribuição, mantendo o código mais limpo.
```java
// Recomendado:
var candidatos = candidatoRepository.findAll();
var usuario = new Usuario();

// Evite se o tipo retornado não for óbvio:
var resultado = processador.executar(); // Melhor declarar explicitamente o tipo
```

### 3. Switch Expressions & Pattern Matching
Aproveite o switch moderno para tornar a verificação e atribuição de tipos ou enums mais concisa e legível.
```java
String descricaoContrato = switch (vaga.getTipoContrato()) {
    case CLT -> "Consolidação das Leis do Trabalho";
    case PJ -> "Pessoa Jurídica / Prestação de Serviço";
    case TEMPORARIO -> "Trabalho Temporário";
    default -> throw new IllegalArgumentException("Tipo inválido");
};
```

---

## 🧹 Princípios de Código Limpo (Clean Code)

* **Nomes Autoexplicativos:** Dê nomes descritivos para variáveis, métodos e classes (ex: `buscarCandidatosAtivosPorVaga` em vez de `getUsers`).
* **Métodos Pequenos e Focados:** Cada método deve realizar apenas uma tarefa (Princípio da Responsabilidade Única).
* **Evite Comentários Obvios:** Escreva código autoexplicativo. Use comentários apenas para explicar decisões de design complexas ou regras de negócio confusas.
