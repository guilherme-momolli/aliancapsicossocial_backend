# Camada de Models

Representam as entidades de dominio e sao mapeadas para as tabelas do banco de dados (JPA).

## Regras e Boas Praticas

1. **Uso do Lombok**: Utilize com cautela em entidades JPA (evite @Data, prefira @Getter e @Setter especificos).
2. **Auditoria**: Entidades devem herdar de `AuditableBaseEntity` sempre que necessario o rastreio de criacao e modificacao.
3. **Encapsulamento**: Mantenha os campos privados.
4. **Validacao Bean**: Use anotacoes como @NotNull, @Size para validacoes de integridade no nivel de classe.
