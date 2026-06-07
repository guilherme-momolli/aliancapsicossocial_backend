# 🚨 Boas Práticas para Tratamento de Exceções e Erros da API

Este documento define os padrões e diretrizes para o tratamento, lançamento e formatação de erros e exceções no projeto **Aliança Psicossocial**.

---

## 📐 1. Padrão de Resposta de Erro da API

Toda e qualquer requisição que falhar retornará um payload JSON padronizado com os seguintes campos:

```json
{
  "timestamp": "2026-06-06T22:36:50.123Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Falha na validação dos dados de entrada.",
  "path": "/auth/register",
  "errors": [
    {
      "campo": "senha",
      "mensagem": "A senha deve ter no mínimo 6 caracteres"
    }
  ]
}
```

* O campo `errors` é preenchido apenas em caso de falha de validação dos dados (`MethodArgumentNotValidException`). Em erros gerais, ele é omitido da resposta.

---

## 🛠️ 2. Exceções Customizadas do Projeto

Utilizamos exceções específicas para representar situações de negócios previsíveis. Elas estendem `RuntimeException`:

1. **`RecursoNaoEncontradoException`**: Lançada quando uma entidade/registro não é encontrado pelo ID ou outro identificador único (mapeia automaticamente para o status **`404 Not Found`**).
2. **`RegraDeNegocioException`**: Lançada para violações gerais de regras ou lógica de negócio (mapeia automaticamente para o status **`400 Bad Request`**).

### Exemplo de Uso no Serviço/Controlador:
```java
if (repository.findByEmail(email).isPresent()) {
    throw new RegraDeNegocioException("O e-mail informado já está cadastrado no sistema.");
}
```

---

## 🚫 3. O que EVITAR no Tratamento de Exceções

* > [!WARNING]
  > **Nunca retorne Stack Traces Brutos para o cliente:** Expor o stack trace (linhas de erro do Java/Hibernate) é uma vulnerabilidade de segurança. O handler genérico `Exception.class` no `GlobalExceptionHandler` captura qualquer erro inesperado e responde com uma mensagem genérica, gravando os detalhes reais apenas nos logs do servidor.
  >
  > **Nunca use prints de console (`System.out.println` ou `e.printStackTrace()`):** Use o logging do SLF4J (anotação `@Slf4j` do Lombok) para logar erros, permitindo a correta categorização em níveis de severidade (ex: `log.error("...", ex)`).
  >
  > **Evite capturar exceções genericamente para ocultá-las (Catch-and-Swallow):** Se capturar uma exceção, trate-a ou lance uma exceção de negócio correspondente. Silenciar exceções dificulta o debug da aplicação.

---

## 🚀 4. Como Adicionar Novas Exceções Customizadas

Se uma regra exigir um status HTTP diferente ou um tratamento muito particular:
1. Crie a classe da exceção na pasta `br.com.aliancapsicossocial.exceptions` estendendo `RuntimeException`.
2. Adicione um método correspondente com a anotação `@ExceptionHandler(SuaClasseException.class)` dentro da classe `GlobalExceptionHandler`.
3. Defina o código de status HTTP apropriado a ser retornado no `ResponseEntity`.
