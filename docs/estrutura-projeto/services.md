# Camada de Services

A camada de Service detem as regras de negocio e orquestra os casos de uso da aplicacao.

## Regras e Boas Praticas

1. **Logica Centralizada**: Toda validacao de negocio e calculo deve estar aqui.
2. **Uso de Helpers**: Para manter o Clean Code, o Service deve delegar validacoes de dominio para a camada de **Helpers**.
3. **Injeçao de Dependencia**: Use injeçao via construtor (preferencialmente via Lombok @RequiredArgsConstructor).
4. **Transacionalidade**: Utilize @Transactional em metodos que alteram o estado do banco de dados.
5. **Isolamento**: Services nao devem conhecer detalhes de HTTP (Controllers) ou de Banco de Dados direto (SQL).
6. **Nomenclatura**: Devem terminar com o sufixo `Service` (ex: AutenticacaoService).
