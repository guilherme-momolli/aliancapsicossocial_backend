# Auditoria Corporativa Defense in Depth

Este guia descreve a arquitetura implementada para auditoria em quatro camadas. A meta e garantir rastreabilidade mesmo quando uma camada isolada nao responde a todas as perguntas de auditoria.

## Matriz de Responsabilidades

| Camada | Tecnologia | Responde |
| --- | --- | --- |
| Metadados da entidade | Spring Data JPA Auditing | Quem criou ou atualizou o registro atual? |
| Historico temporal | Hibernate Envers | Qual era o estado anterior do registro? |
| Eventos de seguranca | Spring Security Events | Quem autenticou ou falhou ao autenticar? |
| Logs estruturados | SLF4J MDC + Logback JSON | Qual request, IP e usuario geraram o evento? |

## Fluxo de Request Autenticada

1. O `SecurityFilter` valida o JWT e popula o `SecurityContextHolder`.
2. O `AuditMdcFilter` cria `traceId`, resolve `clientIp` e identifica `username`.
3. O controller ou service executa a regra de negocio.
4. O Spring Data JPA preenche os campos de `AuditableBaseEntity`.
5. O Envers persiste uma revisao quando uma entidade `@Audited` muda.
6. O Logback emite eventos JSON contendo as propriedades do MDC.
7. O bloco `finally` do filtro executa `MDC.clear()`.

## Regras de Seguranca

* Nunca registrar senhas, tokens, chaves privadas ou secrets.
* Nunca depender de `X-Forwarded-For` sem controle do proxy de borda.
* Configurar o proxy/load balancer para sobrescrever headers de IP recebidos da internet.
* Aplicar controle de acesso aos indices ou tabelas de auditoria.
* Definir retencao conforme requisitos legais e politicas internas.

## Convencoes

* Auditor sem usuario autenticado: `system`.
* Logger exclusivo de seguranca: `SECURITY_AUDIT`.
* Tabela global de revisoes: `sys_revision_info`.
* Sufixo das tabelas Envers: `_aud`.
* Campos MDC: `traceId`, `clientIp`, `username`.

## Operacao

Em producao, os logs JSON devem ser coletados diretamente do stdout do container. A plataforma de observabilidade deve indexar `traceId`, `clientIp`, `username`, `logger`, `level` e `event`.

Consultas de investigacao devem correlacionar:

* `traceId` nos logs aplicacionais.
* `username` em `sys_revision_info`.
* Linhas das tabelas `_aud`.
* Eventos do logger `SECURITY_AUDIT`.

## Qualidade

Alteracoes nessa infraestrutura exigem compilacao completa e revisao dos logs gerados em uma request autenticada e em uma tentativa de login invalida. Mudancas em entidades auditadas tambem devem validar se os campos corretos entram ou nao entram nas tabelas `_aud`.
