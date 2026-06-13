# Diretrizes Técnicas de Testes: Java Spring Boot

Este documento estabelece as regras arquiteturais, convenções de código e padrões de mercado obrigatórios para a criação da suíte de testes do projeto. O objetivo é garantir testes rápidos, isolados, legíveis e fáceis de manter.

## 1. Localização e Organização

### 1.1 Pasta de Testes
* Todos os testes devem residir obrigatoriamente em `src/test/java`.
* A estrutura de pacotes deve espelhar exatamente a estrutura de `src/main/java`.
* **Proibido:** Colocar classes de teste ou mocks dentro de `src/main/java`.

### 1.2 Sufixos de Arquivos
* **Testes Unitários:** Devem terminar com o sufixo `Test.java` (Ex: `UsuarioServiceTest.java`).
* **Testes de Integração:** Devem terminar com o sufixo `IT.java` (Ex: `UsuarioRepositoryIT.java`).
* **Testes de Arquitetura:** Devem terminar com o sufixo `ArchTest.java`.

## 2. Categorias de Testes

### 2.1 Testes Unitários (Business Logic)
* **Objetivo:** Validar a lógica de uma única classe de forma isolada.
* **Regra de Ouro:** NUNCA carregar o contexto do Spring (`@SpringBootTest`, `@Autowired`).
* **Stack:** JUnit 5 + Mockito + AssertJ.
* **Configuração:** Usar `@ExtendWith(MockitoExtension.class)`.

### 2.2 Testes de Fatia (Slice Tests - Web)
* **Objetivo:** Validar controladores, roteamento, serialização e segurança básica.
* **Anotação:** `@WebMvcTest(Controller.class)`.
* **Stack:** `MockMvc` para chamadas HTTP e `@MockitoBean` (ou `@MockBean`) para as dependências.

### 2.3 Testes de Integração (Persistence/External)
* **Objetivo:** Validar a integração entre componentes ou com o banco de dados.
* **Anotação:** `@DataJpaTest` para persistência ou `@SpringBootTest` para integração completa.
* **Banco de Dados:** Preferencialmente usar H2 em memória ou Testcontainers (PostgreSQL) para maior fidelidade ao ambiente de prod.

## 3. Padrões de Escrita (Clean Code)

### 3.1 Padrão AAA (Arrange, Act, Assert)
Todo método de teste deve ser dividido em três blocos claros:
```java
@Test
@DisplayName("Deve salvar um usuário com sucesso quando os dados forem válidos")
void deveSalvarUsuarioComSucesso() {
    // Arrange (Cenário/Dado) - Prepara os mocks e inputs
    var usuario = UsuarioBuilder.novo().build();
    when(repository.save(any())).thenReturn(usuario);

    // Act (Ação/Quando) - Executa o método testado
    var resultado = service.salvar(usuario);

    // Assert (Validação/Então) - Verifica o resultado
    assertThat(resultado).isNotNull();
    verify(repository).save(usuario);
}
```

### 3.2 Nomenclatura de Métodos
* Use nomes descritivos em português (ou inglês, se preferir manter o padrão do código) que expliquem o **comportamento esperado** e a **condição**.
* Exemplo: `deveLancarExcecao_QuandoEmailJaCadastrado` ou `shouldReturnUser_WhenIdExists`.

### 3.3 Asserções
* Use exclusivamente **AssertJ** (`assertThat`). É mais legível e fluente que as asserções nativas do JUnit.
* **Evite:** `assertTrue`, `assertEquals` do JUnit.

## 4. Melhores Práticas e Restrições

### 4.1 Isolamento
* Um teste não deve depender do resultado de outro teste.
* Use `@BeforeEach` para setup comum e garanta que o estado seja limpo após cada execução.

### 4.2 Mocking
* Mocke apenas dependências externas à classe sendo testada.
* Não use mocks para objetos simples de valor (DTOs, Entidades), prefira instanciá-los ou usar Builders.
* **Evite `@Spy`** a menos que seja estritamente necessário para testar classes legadas.

### 4.3 Cobertura e Qualidade
* Foco em caminhos críticos e regras de negócio complexas.
* Não teste código gerado automaticamente (getters/setters do Lombok).
* Teste cenários de erro (exceções) com `assertThatThrownBy`.

### 4.4 Proibições
* **PROIBIDO** usar `System.out.println` nos testes. Use logs se necessário.
* **PROIBIDO** deixar testes comentados ou usando `@Disabled` sem uma justificativa clara (ticket/issue).
* **PROIBIDO** lógica complexa (if/for) dentro dos testes. Testes devem ser simples e lineares.
