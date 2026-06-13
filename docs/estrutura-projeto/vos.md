# Camada de VOs (Value Objects)

Representam objetos que sao definidos por seus atributos e nao por uma identidade unica (ID).

## Responsabilidades

1. **Integridade de Dados**: Garantir que um conjunto de dados (ex: Endereco, CPF, Telefone) seja sempre valido como um todo.
2. **Imutabilidade**: Value Objects devem ser imutaveis (use Records do Java 21).

## Boas Praticas

1. **Sem Identidade**: Diferente de Entidades (Models), dois VOs sao iguais se seus valores forem iguais.
2. **Logica Interna**: Podem conter logica de validacao propria no construtor.
3. **Nomenclatura**: Devem refletir o conceito de negocio (ex: `Cpf`, `Endereco`).
