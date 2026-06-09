# Boas Praticas de Auditoria Corporativa

Este documento define o padrao de auditoria corporativa do projeto Alianca Psicossocial em banco relacional, seguindo uma abordagem de Defense in Depth. A auditoria nao depende de um unico mecanismo: metadados de entidade, historico temporal, eventos de seguranca e logs estruturados trabalham em conjunto.

## Objetivos

* Registrar quem criou e alterou registros persistidos por JPA.
* Manter historico temporal de alteracoes relevantes por Hibernate Envers.
* Registrar sucessos e falhas de autenticacao em um logger exclusivo.
* Propagar contexto operacional por request usando MDC.
* Produzir logs JSON prontos para coleta por observabilidade corporativa.

## Camada 1: Metadados JPA

A classe `AuditableBaseEntity` deve ser herdada por entidades que precisam dos campos comuns:

* `criado_por`
* `data_criacao`
* `atualizado_por`
* `data_atualizacao`

Regras obrigatorias:

* Nunca preencher estes campos manualmente em controllers ou services.
* Usar `@CreatedBy`, `@CreatedDate`, `@LastModifiedBy` e `@LastModifiedDate`.
* Configurar `updatable = false` nos campos de criacao.
* Retornar `system` quando nao houver usuario autenticado.

O auditor atual e resolvido por `AuditorAwareImpl`, que consulta o `SecurityContextHolder` e trata principals anonimos, nulos ou sem nome.

## Camada 2: Historico Temporal com Envers

Entidades com valor de negocio devem usar `@Audited`. O Envers cria tabelas historicas com sufixo `_aud` e grava as revisoes globais em `sys_revision_info`.

Padroes adotados:

* Tabelas de auditoria terminam com `_aud`.
* Deletes armazenam os dados completos do registro removido.
* A tabela `sys_revision_info` registra `revision_id`, `revision_timestamp` e `username`.
* Campos volateis, internos ou sem relevancia historica devem usar `@NotAudited`.

A entidade `Produto` demonstra o uso correto:

* `nome`, `sku` e `preco` sao auditados.
* `observacaoInterna` usa `@NotAudited`.

## Camada 3: Eventos de Seguranca

O componente `SecurityAuditEventListener` captura eventos nativos do Spring Security:

* `AuthenticationSuccessEvent`
* `AbstractAuthenticationFailureEvent`

Os eventos sao processados com `@Async` e enviados ao logger `SECURITY_AUDIT`. Esse logger deve ser roteado separadamente pela plataforma de logs para permitir retencao, alertas e consultas especificas de seguranca.

## Camada 4: MDC e Logs Estruturados

O filtro `AuditMdcFilter` injeta contexto por requisicao no MDC do SLF4J:

* `traceId`: UUID aleatorio por request.
* `clientIp`: IP real, respeitando `X-Forwarded-For`.
* `username`: usuario autenticado ou `system`.

O MDC deve sempre ser limpo no bloco `finally` com `MDC.clear()`. Essa regra e obrigatoria porque servidores web reutilizam threads; sem limpeza, dados de uma request podem contaminar outra.

## Logback JSON

O arquivo `logback-spring.xml` configura um `ConsoleAppender` JSON com `logstash-logback-encoder`. O provider `<mdc/>` inclui automaticamente `traceId`, `clientIp` e `username` no evento de log.

Padroes operacionais:

* Logs devem ser enviados para stdout em containers.
* O collector deve parsear JSON nativo, sem regex.
* O logger `SECURITY_AUDIT` deve ter indice, stream ou rota propria.
* Dados sensiveis como senha, token JWT, CPF e segredos nunca devem ser logados.

## Banco Relacional e Migracoes

Em ambientes controlados, preferir Flyway para criar tabelas de dominio e auditoria. O `ddl-auto=update` pode acelerar desenvolvimento local, mas nao deve ser usado como mecanismo principal de evolucao de schema em homologacao e producao.

Ao criar novas entidades auditadas:

* Criar migration da tabela principal.
* Validar a tabela `_aud` gerada ou versionar sua criacao conforme a estrategia de deploy.
* Confirmar indices para chaves de negocio consultadas com frequencia.
* Confirmar que campos `@NotAudited` realmente nao precisam de historico.

## Checklist para Novas Entidades

* Herdar de `AuditableBaseEntity` quando houver persistencia rastreavel.
* Usar `@Audited` quando o historico temporal for necessario.
* Usar `@NotAudited` em campos volateis, calculados, temporarios ou sem valor legal.
* Evitar logs com payload completo de entidades.
* Criar testes para persistencia quando a entidade fizer parte de fluxo critico.
* Revisar retencao e acesso aos dados auditados antes de armazenar informacao sensivel.
