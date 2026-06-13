# Camada de Exceptions

Gerenciamento centralizado de erros e excecoes da aplicacao.

## Regras e Boas Praticas

1. **GlobalExceptionHandler**: Use @ControllerAdvice para capturar excecoes em um unico ponto e retornar JSON padronizado.
2. **Excecoes de Negocio**: Crie excecoes customizadas (ex: RegraDeNegocioException) para cenarios especificos.
3. **Nao vazar StackTrace**: Nunca retorne o stacktrace para o cliente em ambiente de producao.
4. **Padrao de Resposta**: Use um DTO padrao (ex: ErroResponseDTO) para todas as respostas de erro.
