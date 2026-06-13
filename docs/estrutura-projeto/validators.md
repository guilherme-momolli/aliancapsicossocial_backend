# Camada de Validators

Os Validators sao as unidades mais granulares de validacao do sistema, focados em regras especificas para variaveis ou campos isolados.

## Responsabilidades

1. **Validacao Atomica**: Verificar regras simples como formato de email, CPF, força de senha ou intervalos numericos.
2. **Reutilizacao**: Serem reaproveitados por diversos Helpers ou ate mesmo por diferentes Services.
3. **Lancamento de Excecoes**: Devem lancar excecoes especificas (ex: RegraDeNegocioException) quando a validacao falha.

## Boas Praticas

1. **Foco Unico**: Cada validador deve idealmente validar apenas uma coisa ou um conjunto muito estreito de regras relacionadas.
2. **Interface Comum**: Em sistemas complexos, podem implementar uma interface `Validator<T>`.
3. **Nomenclatura**: Devem terminar com o sufixo `Validator` (ex: EmailValidator, SenhaValidator).

## Por que usar?

Separar a validacao de uma variavel em um Validator garante que a mesma regra seja aplicada em todo o sistema, evitando duplicacao de codigo e inconsistencias.
