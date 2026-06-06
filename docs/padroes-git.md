# 📌 Padrões de Git e Commits Semânticos

Este documento define os padrões de controle de versão e commits para o projeto **Aliança Psicossocial**.

---

## 🚀 Commits Semânticos (Conventional Commits)

Adotamos a convenção de commits semânticos para manter o histórico do Git limpo, legível e automatizável. Cada commit deve conter um prefixo que descreve a natureza da alteração.

### Prefixo e Descrição

| Prefixo | Descrição | Exemplo |
| :--- | :--- | :--- |
| `feat` | Implementação de uma nova funcionalidade | `feat: adiciona envio de e-mail de confirmação` |
| `fix` | Correção de um erro ou bug | `fix: corrige validação de CNPJ do empregador` |
| `docs` | Alterações apenas em documentação | `docs: detalha guia de deployment do ECS` |
| `style` | Formatações de código (espaços, identação, sem mudança de lógica) | `style: formata identação no UsuarioService` |
| `refactor` | Refatoração de código que não altera o comportamento da API | `refactor: otimiza query de busca de vagas` |
| `test` | Adição ou alteração de testes automatizados | `test: adiciona teste unitário para login` |
| `chore` | Mudanças de build, dependências ou ferramentas de suporte | `chore: atualiza versão do driver PostgreSQL` |

---

## 💡 Boas Práticas para Branching e Pull Requests

1. **Mensagens Claras:** Escreva a mensagem de commit no imperativo (ex: "adiciona" em vez de "adicionado" ou "adicionando").
2. **Commits Pequenos:** Faça commits focados em uma única alteração. Evite comitar múltiplos arquivos sem relação no mesmo commit.
3. **Nomes de Branch:** Use padrões descritivos para ramificações, por exemplo:
   * `feature/nome-da-funcionalidade`
   * `bugfix/correcao-do-problema`
   * `docs/nome-do-documento`
