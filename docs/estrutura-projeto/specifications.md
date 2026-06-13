# Camada de Specifications

Implementa o padrao Specification do Domain-Driven Design (DDD) para criar queries dinamicas e reutilizaveis.

## Responsabilidades

1. **Filtros Dinamicos**: Permitir que APIs de busca combinem diversos parametros (nome, data, status) de forma flexivel.
2. **Abstracao de Query**: Remover a complexidade de criterios de busca dos Repositories e Services.

## Boas Praticas

1. **Reutilizacao**: Combine especificacoes simples (ex: `comNome`, `estaAtivo`) para formar consultas complexas.
2. **Uso de Criteria API**: Utilize as facilidades do JPA Criteria API para garantir segurança de tipos nas queries.
3. **Nomenclatura**: Devem terminar com o sufixo `Specification` (ex: `ProdutoSpecification`).
