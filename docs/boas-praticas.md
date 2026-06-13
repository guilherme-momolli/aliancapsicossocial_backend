# 💻 Boas Práticas e Padrões do Projeto

Este é o índice central de boas práticas e padrões de desenvolvimento adotados no **Aliança Psicossocial**. Os tópicos foram divididos em arquivos focados e leves para melhor legibilidade e entendimento por outros agentes de IA.

---

## 📚 Documentos Específicos

* **[☕ Java 21 & Código Limpo](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-java.md):** Padrões de escrita em Java 21, uso de Records, switch expressions modernizados e princípios gerais de Clean Code.
* **[🍃 Spring Boot & Arquitetura](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-spring.md):** Padrão de camadas da aplicação, injeção de dependência pelo construtor e tratamento global de erros.
* **[🏷️ Guia do Lombok com JPA](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/guia-lombok-jpa.md):** Guia de uso correto das anotações Lombok em entidades do Hibernate/JPA para evitar erros de StackOverflow.
* **[🔑 JWT & Autenticação Stateless](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-jwt.md):** Diretrizes para geração, validação e segurança de tokens JWT com Auth0.
* **[🗄️ Flyway & Migrações de Banco](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-flyway.md):** Padrões de nomenclatura, versionamento e regras de imutabilidade para evolução do banco de dados.
* **[Auditoria Corporativa](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-auditoria.md):** Padrão Defense in Depth para metadados JPA, Envers, eventos de segurança e logs estruturados.
* **[Auditoria Defense in Depth](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/auditoria-corporativa-defense-in-depth.md):** Guia operacional para correlação entre MDC, logs JSON, eventos de autenticação e tabelas `_aud`.
* **[📌 Git & Padrões de Commit](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/padroes-git.md):** Regras de commits semânticos (Conventional Commits) e nomenclatura de branches.
* **[🚨 Tratamento de Exceções](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-exceptions.md):** Diretrizes para o lançamento de exceções de negócio e formatação de respostas JSON padronizadas de erros.
* **[🤖 AI Agent Guidelines](../GEMINI.md):** Regras de operação, escrita e arquitetura para assistentes de inteligência artificial.
* **[🏗️ Estrutura do Projeto](estrutura-projeto/index.md):** Padroes de pastas, camadas e responsabilidades seguindo Clean Architecture.

---

## 🤝 Por que a divisão dos arquivos?

Cada documento é focado em uma única responsabilidade (Single Responsibility Principle aplicado à documentação). Isso reduz a quantidade de texto que agentes de IA precisam processar ao resolver tarefas específicas no futuro, otimizando o consumo de tokens e a precisão da geração de código.
