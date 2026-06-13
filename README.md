#  Aliança Psicossocial - Backend (Sistema de RH)

Este é o backend do **Aliança Psicossocial**, um sistema completo e moderno de recrutamento, seleção e gestão de recursos humanos (RH), construído utilizando as melhores práticas do mercado, **Java 21**, **Spring Boot 4** e **PostgreSQL**.

A plataforma foi desenhada para atender a dois perfis principais de usuários (clientes), suportando tanto Pessoas Físicas (PF) quanto Pessoas Jurídicas (PJ).

---

##  Sumário

- [ Propósito do Projeto](#propósito-do-projeto)
- [️ Tecnologias Principais](#tecnologias-principais)
- [ Dependências do Projeto (pom.xml)](#dependências-do-projeto-pomxml)
- [️ Arquitetura e Integração AWS](#arquitetura-e-integração-aws)
- [ Estrutura de Diretórios e Documentos](#estrutura-de-diretórios-e-documentos)
- [ Documentação e Boas Práticas](#documentação-e-boas-práticas)
- [️ Arquitetura de Dados (Modelagem Conceitual)](#arquitetura-de-dados-modelagem-conceitual)
- [ Como Executar o Projeto](#como-executar-o-projeto)
  - [Pré-requisitos](#pré-requisitos)
  - [1. Configuração de Variáveis de Ambiente](#1-configuração-de-variáveis-de-ambiente)
  - [2. Executando via Docker (Recomendado)](#2-executando-via-docker-recomendado)
  - [3. Executando Localmente (Sem Docker)](#3-executando-localmente-sem-docker)
- [ Documentação da API (Swagger)](#documentação-da-api-swagger)
- [ Contribuição e Próximos Passos](#contribuição-e-próximos-passos)

---


##  Propósito do Projeto

O sistema conecta quem deseja trabalhar com quem precisa contratar, com flexibilidade total para diferentes tipos de arranjos de trabalho (CLT, PJ, Temporário, MEI). A plataforma suporta:

1. **Candidato**:
   - **Pessoa Física (PF)**: Candidatos tradicionais buscando vagas CLT, estágio ou prestação de serviço.
   - **Pessoa Jurídica (PJ)**: Profissionais independentes ou empresas parceiras oferecendo prestação de serviços a corporações.
2. **Empregador**:
   - **Pessoa Jurídica (PJ / MEI)**: Empresas consolidadas ou Microempreendedores Individuais que publicam vagas e gerenciam processos de seleção.
   - **Pessoa Física (PF)**: Indivíduos contratantes que buscam profissionais para serviços pontuais, domésticos ou freelas.

---

## ️ Tecnologias Principais

A stack tecnológica do projeto foi definida para garantir estabilidade, segurança e alto desempenho:

- **Linguagem**: Java 21 (LTS)
- **Framework Principal**: Spring Boot 4.0.6
- **Banco de Dados**: PostgreSQL (Relacional)
- **Camada de Persistência**: Spring Data JPA & Hibernate
- **Segurança**: Spring Security (Autenticação Básica e futuro JWT)
- **Documentação de API**: Springdoc OpenAPI / Swagger UI
- **Build Tool**: Maven

---

##  Dependências do Projeto (pom.xml)

Abaixo estão detalhadas as dependências atualmente configuradas no arquivo `pom.xml`:

* **Spring Boot Starter Web MVC**: Utilizado para construção e exposição dos endpoints REST da API.
* **Spring Boot Starter Data JPA**: Camada de persistência utilizando Hibernate para mapeamento objeto-relacional (ORM).
* **Spring Boot Starter Security**: Configura a camada de segurança para autenticação e autorização das requisições.
* **Spring Boot Starter Mail**: Integração para envio de e-mails e notificações transacionais pelo sistema.
* **Spring Boot Starter Jersey**: Suporte complementar para especificações JAX-RS.
* **Spring Boot Starter RestClient**: Novo cliente HTTP nativo do Spring Boot para consumo de APIs externas.
* **Springdoc OpenAPI UI (v3.0.2)**: Gera automaticamente a documentação OpenAPI 3 e expõe a interface interativa do **Swagger UI**.
* **PostgreSQL Driver**: Driver JDBC necessário para a conexão nativa com o banco de dados PostgreSQL.
* **Lombok**: Utilitário para redução de boilerplate (gerando getters, setters, equals, hashCode e construtores via anotações).
* **Hibernate Core**: Framework ORM declarado explicitamente para mapeamento e consultas a banco de dados.
* **Auth0 Java-JWT (v4.4.0)**: Biblioteca leve e robusta para geração, validação e assinatura de JSON Web Tokens (JWT).
* **Flyway (Core & PostgreSQL)**: Engine de migrações para gerenciar o controle de versão evolutivo do esquema de banco de dados.

---

##  Arquitetura e Integração AWS

O projeto está desenhado para rodar e integrar-se com a nuvem da **AWS (Amazon Web Services)**. Os principais componentes em fase de desenho e implementação são:

* **AWS ECS (Fargate) & ALB**: Hospedagem containerizada e serverless da aplicação, garantindo escalabilidade automática sem gerenciar servidores.
* **Amazon RDS (PostgreSQL)**: Banco de dados relacional gerenciado pela AWS com backups diários automáticos.
* **Amazon S3**: Armazenamento privado para currículos dos candidatos (PDF/DOCX) e imagens/logos da plataforma.
* **Amazon SES**: Envio confiável de e-mails transacionais (como avisos de novas candidaturas ou alteração de status).
* **AWS Secrets Manager**: Armazenamento e rotação segura de credenciais do banco e chaves de segurança da API.

> [!TIP]
> O planejamento detalhado das etapas de integração, variáveis de ambiente necessárias e o diagrama da infraestrutura podem ser acessados em: [☁️ AWS & Integrações](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/aws-arquitetura.md).

---

##  Estrutura de Diretórios e Documentos

O projeto segue um padrão arquitetural em camadas no pacote principal `br.com.aliancapsicossocial` e mantém documentações auxiliares e de boas práticas modularizadas na pasta `docs/`:

```text
aliancapsicossocial/
├── docs/                     # Documentações detalhadas e leves do projeto
│   ├── boas-praticas.md      # Índice geral de boas práticas
│   ├── boas-praticas-java.md # Padrões de desenvolvimento em Java 21 e Clean Code
│   ├── boas-praticas-spring.md # Estrutura do Spring Boot e injeção de dependências
│   ├── guia-lombok-jpa.md    # Guia para uso correto do Lombok com JPA
│   ├── boas-praticas-jwt.md  # Diretrizes de implementação e segurança para JWT
│   ├── boas-praticas-flyway.md # Boas práticas de migrações SQL com Flyway
│   ├── padroes-git.md        # Convenções de Git e commits semânticos
│   └── aws-arquitetura.md    # Arquitetura e planejamento da infraestrutura AWS
├── envs/                     # Arquivos de configuração de variáveis de ambiente (.env)
├── src/main/java/br/com/aliancapsicossocial/
│   ├── configurations/       # Configurações gerais (Segurança, Swagger, CORS, Beans)
│   ├── controllers/          # Controladores REST que expõem os endpoints da API
│   ├── models/               # Entidades JPA que representam as tabelas do banco
│   ├── repositories/         # Interfaces de acesso ao banco (Spring Data JPA)
│   ├── services/             # Camada de lógica de negócio e regras do sistema
│   ├── exceptions/           # Tratamento global de erros e exceções customizadas
│   ├── mappers/              # Conversão entre Entidades e DTOs/VOs
│   ├── validators/           # Validadores customizados (ex: validação de CPF/CNPJ)
│   ├── vos/                  # Value Objects / DTOs (Data Transfer Objects)
│   ├── specifications/       # Filtros avançados de consulta usando JPA Criteria
│   └── helpers/              # Classes utilitárias diversas
```

---

##  Documentação e Boas Práticas

Para manter a documentação leve e otimizada para desenvolvedores e agentes de Inteligência Artificial, dividimos as diretrizes técnicas do projeto em módulos focados e de responsabilidade única. Acesse cada guia diretamente através dos links abaixo:

* **[ Índice de Boas Práticas](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas.md):** Ponto de entrada geral contendo o resumo dos padrões adotados.
* **[ Java 21 & Código Limpo](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-java.md):** Recursos modernos (Records, switches, etc.) e legibilidade de código.
* **[ Spring Boot & Arquitetura](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-spring.md):** Separação de camadas (MVC), injeção via construtor e tratamento global de erros.
* **[ Lombok com JPA](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/guia-lombok-jpa.md):** Evitando o StackOverflowError e LazyInitializationException em entidades relacionais.
* **[ JWT & Autenticação Stateless](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-jwt.md):** Diretrizes para geração, validação e segurança de tokens JWT com Auth0.
* **[ Flyway & Migrações de Banco](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/boas-praticas-flyway.md):** Padrões de nomenclatura, versionamento e regras de imutabilidade para evolução do banco de dados.
* **[ Padrões de Git & Commits](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/padroes-git.md):** Nossos padrões de Conventional Commits e fluxo de branches.
* **[ Arquitetura AWS & Nuvem](file:///c:/Users/Guilherme/OneDrive/Documentos/programing/Backend/aliancapsicossocial/docs/aws-arquitetura.md):** Desenho da infraestrutura cloud-native planejado para deploy e integrações da API.

---

##  Arquitetura de Dados (Modelagem Conceitual)

A base de dados foi modelada para suportar a flexibilidade dos clientes PF/PJ por meio de relacionamentos um-para-um e herança lógica de perfis:

### 1. Tabela `usuarios`
Centraliza a autenticação de todos os usuários do sistema.
- `id` (UUID, PK)
- `email` (VARCHAR, Unique)
- `senha` (VARCHAR, Hash)
- `role` (ENUM: `CANDIDATO`, `EMPREGADOR`, `ADMIN`)
- `ativo` (BOOLEAN)
- `data_criacao` (TIMESTAMP)

### 2. Tabela `perfis_candidatos`
Atributos específicos do Candidato.
- `id` (UUID, PK, FK para `usuarios`)
- `tipo_pessoa` (ENUM: `FISICA`, `JURIDICA`)
- `documento` (VARCHAR, Unique) -> CPF ou CNPJ
- `nome_completo` ou `razao_social` (VARCHAR)
- `telefone` (VARCHAR)
- `resumo_profissional` (TEXT)
- `habilidades` (TEXT / JSON)

### 3. Tabela `perfis_empregadores`
Atributos específicos do Empregador (Empresas, MEI ou Contratante PF).
- `id` (UUID, PK, FK para `usuarios`)
- `tipo_pessoa` (ENUM: `FISICA`, `JURIDICA`)
- `documento` (VARCHAR, Unique) -> CPF ou CNPJ
- `nome_fantasia` ou `nome` (VARCHAR)
- `setor` (VARCHAR)
- `site` (VARCHAR)
- `descricao` (TEXT)

### 4. Tabela `vagas`
Vagas de emprego criadas pelos empregadores.
- `id` (UUID, PK)
- `empregador_id` (UUID, FK para `perfis_empregadores`)
- `titulo` (VARCHAR)
- `descricao` (TEXT)
- `requisitos` (TEXT)
- `salario` (NUMERIC)
- `tipo_contrato` (ENUM: `CLT`, `PJ`, `TEMPORARIO`, `FREELANCE`)
- `status` (ENUM: `ATIVA`, `PAUSADA`, `ENCERRADA`)

### 5. Tabela `candidaturas`
Associação entre Candidato e Vaga (Processo Seletivo).
- `id` (UUID, PK)
- `vaga_id` (UUID, FK para `vagas`)
- `candidato_id` (UUID, FK para `perfis_candidatos`)
- `status` (ENUM: `RECEBIDO`, `TRIAGEM`, `ENTREVISTA`, `APROVADO`, `REJEITADO`)
- `data_candidatura` (TIMESTAMP)

---

##  Como Executar o Projeto

### Pré-requisitos

- **Docker** e **Docker Compose** instalados (Recomendado).
- **Java Development Kit (JDK) 21** e **PostgreSQL** instalado localmente (apenas para execução manual sem Docker).

---

### 1. Configuração de Variáveis de Ambiente

As configurações de ambiente estão localizadas na pasta `envs/`. Copie o arquivo de exemplo para o arquivo real correspondente ao ambiente desejado:

- **Desenvolvimento**:
  ```bash
  cp envs/.env.dev.example envs/.env.dev
  ```
- **Homologação**:
  ```bash
  cp envs/.env.homolog.example envs/.env.homolog
  ```
- **Produção**:
  ```bash
  cp envs/.env.prod.example envs/.env.prod
  ```

Edite os arquivos `.env.<ambiente>` correspondentes adicionando as senhas e credenciais corretas. 

---

### 2. Executando via Docker (Recomendado)

O Docker Compose gerencia automaticamente a inicialização do banco de dados PostgreSQL e a compilação/execução da API Spring Boot.

####  Ambiente de Desenvolvimento (Dev)
Sobe o banco PostgreSQL, o PgAdmin (para visualizar o banco) e o backend em modo de desenvolvimento:
```bash
docker compose --env-file envs/.env.dev up --build
```
- **API**: `http://localhost:8080`
- **PgAdmin**: `http://localhost:5050` (Email: `admin@admin.com` | Senha: `admin`)
- **PostgreSQL**: `localhost:5432`

####  Ambiente de Homologação (Staging)
Sobe um ambiente de testes isolado usando a porta alternativa `8081` para a API e `5433` para o banco:
```bash
docker compose -f docker-compose.homolog.yml --env-file envs/.env.homolog up --build -d
```
- **API (Homolog)**: `http://localhost:8081`

####  Ambiente de Produção (Prod)
Sobe o banco de dados (protegido na rede interna do Docker) e o backend mapeado para a porta `80` (HTTP):
```bash
docker compose -f docker-compose.prod.yml --env-file envs/.env.prod up --build -d
```
- **API (Prod)**: `http://localhost:80`

Para derrubar os containers do docker compose:
```bash
# Exemplo para desenvolvimento
docker compose down
```

---

### 3. Executando Localmente (Sem Docker)

Se preferir rodar a API direto na sua máquina (fora do container):

1. Certifique-se de que o **PostgreSQL** local está ativo e crie o banco de dados `aliancapsicossocial`.
2. Configure as credenciais no arquivo `application.properties` ou defina as seguintes variáveis no seu terminal:
   ```bash
   export DB_HOST=localhost
   export DB_USERNAME=seu_usuario
   export DB_PASSWORD=sua_senha
   ```
3. Execute o comando Maven na raiz do projeto:
   - **No Windows (PowerShell/CMD)**:
     ```powershell
     ./mvnw spring-boot:run
     ```
   - **No Linux/macOS**:
     ```bash
     chmod +x mvnw
     ./mvnw spring-boot:run
     ```


---

##  Documentação da API (Swagger)

Com a aplicação em execução, você pode interagir com os endpoints e visualizar os contratos de dados acessando:

- **Swagger UI (Desenvolvimento)**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Swagger UI (Homologação)**: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

> [!IMPORTANT]
> **Credenciais de Acesso (Basic Auth):**
> A API está protegida temporariamente por autenticação básica em desenvolvimento e homologação.
> - **Usuário**: `admin`
> - **Senha**: `admin` (ou a senha configurada no seu arquivo `.env.<ambiente>`)
> 
> As variáveis correspondentes nos arquivos `.env` são:
> - `SPRING_SECURITY_USER_NAME`
> - `SPRING_SECURITY_USER_PASSWORD`

---

## Contribuição e Próximos Passos

1. Configuração das Entidades Principais (`Usuario`, `PerfilCandidato`, `PerfilEmpregador`).
2. Configuração do Spring Security com autenticação JWT.
3. Criação dos Repositórios e Serviços iniciais.
4. Implementação de endpoints CRUD para gestão de vagas e candidaturas.
