# Camada de Repositories

Interfaces que estendem Spring Data JPA para acesso aos dados.

## Regras e Boas Praticas

1. **Abstracao**: O Service nao deve saber como o dado e persistido, apenas chama o metodo do repository.
2. **Query Methods**: Use as facilidades do Spring Data para criar queries por nome de metodo sempre que possivel.
3. **Custom Queries**: Use @Query para operacoes complexas, preferindo JPQL sobre SQL nativo por portabilidade.
4. **Nomenclatura**: Devem terminar com o sufixo `Repository` (ex: UsuarioRepository).
