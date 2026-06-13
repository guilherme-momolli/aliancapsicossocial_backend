# Padrões de Geração de Markdown para IA

Este documento define como os agentes de IA devem formatar suas respostas e gerar arquivos Markdown para evitar redundância e maximizar a eficiência de tokens.

## Prevenção de Repetição e Redundância

1. **Sem Prefácios ou Posfácios:** Evite frases como "Aqui está o arquivo solicitado" ou "Espero que isso ajude". Vá direto ao ponto.
2. **Contexto Incremental:** Não repita blocos de código inteiros se apenas uma linha mudou, a menos que seja solicitado explicitamente a escrita completa do arquivo. Utilize ferramentas de edição cirúrgica (como `replace`).
3. **Ponto Único de Verdade:** Se uma informação já existe em outro arquivo `.md` do projeto, faça um link para ele em vez de duplicar o conteúdo.
4. **Listas Concisas:** Prefira listas curtas e diretas sobre parágrafos densos.

## Melhores Padrões de Mercado

1. **Semântica Clara:** Use headers (`#`, `##`, `###`) de forma hierárquica e lógica.
2. **Code Blocks:** Sempre especifique a linguagem no bloco de código (ex: ` ```java `) para sintaxe correta.
3. **Links Relativos:** Sempre use caminhos relativos para referenciar outros arquivos no repositório.

## Clean Documentation Principles

* **Single Responsibility:** Cada arquivo `.md` deve focar em um único assunto. Se um arquivo começar a crescer demais, divida-o.
* **Intenção Clara:** O título e a introdução devem deixar claro o que o documento cobre em menos de 2 sentenças.
* **Auto-explicativo:** O texto deve ser escrito de forma que um novo desenvolvedor (ou agente) entenda o contexto sem precisar ler todo o histórico do projeto.

---
*Referência: Padrões inspirados em Documentação como Código (Docs-as-Code).*
