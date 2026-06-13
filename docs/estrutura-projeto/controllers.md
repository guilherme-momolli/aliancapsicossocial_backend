# Camada de Controllers

Os controllers sao responsaveis por expor os endpoints da aplicacao e lidar com o protocolo HTTP.

## Regras e Boas Praticas

1. **Responsabilidade Unica**: Devem apenas receber a requisicao, chamar o service e retornar a resposta.
2. **Sem Logica de Negocio**: Nenhuma regra de negocio ou validacao complexa deve residir aqui.
3. **Uso de DTOs**: Nunca expor entidades de banco de dados diretamente. Utilize LoginRequestDTO, LoginResponseDTO, etc.
4. **Status HTTP Corretos**:
   - GET: 200 OK
   - POST: 201 Created
   - PUT/PATCH: 200 OK ou 204 No Content
   - DELETE: 204 No Content
5. **Nomenclatura**: Devem terminar com o sufixo `Controller` (ex: AuthController).
