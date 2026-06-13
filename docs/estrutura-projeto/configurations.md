# Camada de Configurations

Centraliza as classes de configuracao do Spring e de bibliotecas de terceiros (AWS, Security, Audit).

## Responsabilidades

1. **Beans do Spring**: Definir e expor beans que nao podem ser anotados com @Component (ex: classes de SDKs).
2. **Segurança**: Configurar o `SecurityFilterChain`, filtros de autenticacao e autorizacao.
3. **Infraestrutura**: Configurações de conexão com cloud (AWS S3, SES) e auditoria (Envers, Hibernate).

## Boas Praticas

1. **Separacao de Interesses**: Crie arquivos de configuracao separados por assunto (ex: `AwsConfig`, `SecurityConfig`, `AuditConfig`).
2. **Uso de Properties**: Sempre utilize `@Value` ou `@ConfigurationProperties` para evitar valores "hardcoded".
3. **Nomenclatura**: Devem terminar com o sufixo `Config` (ex: `AwsConfig`).
