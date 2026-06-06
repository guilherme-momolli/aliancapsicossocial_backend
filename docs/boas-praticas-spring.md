# 🍃 Boas Práticas Spring Boot e Padrões de Arquitetura

Este documento define os padrões arquiteturais e de uso do framework **Spring Boot** para o projeto **Aliança Psicossocial**.

---

## 🏗️ Padrão Arquitetural em Camadas

A API é estruturada em camadas lógicas bem distintas. O fluxo de dados segue o caminho unidirecional:
`Controller` ➡️ `Service` ➡️ `Repository` ➡️ `Banco de Dados`.

* **Controladores (Controllers):**
  * Responsáveis pela camada HTTP. Explicitam rotas, verbos (`GET`, `POST`, etc.) e status codes (`200 OK`, `201 Created`).
  * Devem usar `@Valid` para validar entradas e delegar toda a lógica de processamento para a camada de serviço.
* **Serviços (Services):**
  * Onde reside a lógica de negócios e validações das regras do sistema.
  * Devem ser anotados com `@Service`.
  * Operações de escrita no banco de dados devem usar `@Transactional` para garantir a integridade dos dados (commit/rollback automático).
* **Repositórios (Repositories):**
  * Responsáveis exclusivos pelas operações no banco de dados (Spring Data JPA).

---

## ⚡ Injeção de Dependências

* > [!IMPORTANT]
  > **Use Injeção via Construtor:** Evite usar `@Autowired` diretamente nos campos (Field Injection). A injeção pelo construtor facilita testes de unidade sem inicializar o contexto do Spring e protege as dependências tornando-as `final` (imutabilidade).

```java
// Recomendado:
@Service
public class VagaService {
    private final VagaRepository vagaRepository;

    public VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }
}
```
*(Você pode omitir o construtor explícito usando a anotação `@RequiredArgsConstructor` do Lombok no topo da classe).*

---

## 🚨 Tratamento Global de Exceções

* Não exponha stack traces brutos ou exceções internas (como `SQLException`) para o cliente da API.
* Use a anotação `@RestControllerAdvice` (na camada `exceptions/`) para criar um interceptador global de erros.
* Lançar exceções de negócios específicas (ex: `RecursoNaoEncontradoException`) e mapeá-las para os respectivos códigos HTTP correspondentes (ex: `404 Not Found`).
