# Camada de Helpers

Os Helpers atuam como facilitadores para os Services, concentrando logicas de suporte e validacoes compostas de uma determinada classe ou entidade de dominio.

## Responsabilidades

1. **Validacoes de Dominio**: Agrupar validacoes que verificam o estado consistente de um objeto (ex: UsuarioHelper verifica se o usuario pode ser desativado).
2. **Composicao de Logica**: Utilizar um ou mais Validators para realizar checagens granulares.
3. **Suporte ao Service**: Retirar do Service a carga de "como" validar, permitindo que o Service foque no "que" deve ser feito (fluxo de negocio).

## Boas Praticas

1. **Injeçao de Dependencia**: Devem ser componentes Spring (@Component) para facilitar o uso de Validators e Repositories se necessario.
2. **Sem Estado (Stateless)**: Nao devem manter estado interno que possa causar efeitos colaterais.
3. **Nomenclatura**: Devem terminar com o sufixo `Helper` (ex: UsuarioHelper).

## Exemplo de Fluxo

O Service recebe um DTO, chama o Helper para validar a integridade do dominio, e o Helper utiliza Validators para regras especificas de campos.
