# Clean Standards para IA

Diretrizes para garantir que toda a lógica e estrutura gerada por IA siga os princípios de Clean Code e Clean Architecture.

## Clean Code (Código Limpo)

1. **Nomes Significativos:** Variáveis, classes e métodos devem revelar sua intenção.
2. **Funções Pequenas:** Devem fazer apenas uma coisa (SRP) e ter poucos argumentos.
3. **Comentários Necessários:** Código deve ser auto-explicativo. Use comentários apenas para explicar o "porquê" de decisões não óbvias, nunca o "quê".
4. **Tratamento de Erros:** Deve ser feito de forma isolada e não poluir a lógica principal.

## Clean Architecture (Arquitetura Limpa)

1. **Independência de Frameworks:** A regra de negócio não deve depender de bibliotecas externas.
2. **Testabilidade:** O código deve ser escrito de forma que possa ser testado sem UI, Banco de Dados ou Servidor Web.
3. **Camadas Claras:**
    * **Entities:** Regras de negócio globais.
    * **Use Cases:** Regras de negócio específicas da aplicação.
    * **Interface Adapters:** Mappers, DTOs, Controllers.
    * **Frameworks & Drivers:** Banco de Dados, UI, Dispositivos.

## Separação de Preocupações (SoC)

* **Pastas por Assunto:** Organize o código e a documentação por domínio/assunto, não apenas por tipo de arquivo.
* **Baixo Acoplamento:** Módulos devem interagir através de interfaces bem definidas.
* **Alta Coesão:** Elementos que mudam juntos devem ser mantidos juntos.

---
*Este padrão deve ser aplicado tanto em código quanto na organização da documentação.*
