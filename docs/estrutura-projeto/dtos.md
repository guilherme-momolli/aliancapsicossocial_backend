# Camada de DTOs (Data Transfer Objects)

Objetos simples usados para transportar dados entre as camadas, especialmente entre Controller e Service.

## Regras e Boas Praticas

1. **Imutabilidade**: DTOs de resposta devem preferencialmente ser imutaveis (use Records do Java 21).
2. **Validacao de Entrada**: Use anotacoes de validacao (@Valid, @NotBlank, etc.) nos DTOs de Request.
3. **Desacoplamento**: Evitam que mudancas no banco de dados (Models) afetem diretamente o contrato da API (JSON).
4. **Nomenclatura**: Devem terminar com o sufixo `DTO` (ex: LoginRequestDTO).
