# Estrutura de Pastas e Padroes

Este documento descreve a organizacao de pastas do projeto Alianca Psicossocial, seguindo os principios de Clean Architecture e padroes de mercado para aplicacoes Spring Boot.

## Estrutura Principal

Abaixo, a definicao de cada pacote e sua responsabilidade:

* **[Controllers](controllers.md)**: Porta de entrada da aplicacao (API REST).
* **[Services](services.md)**: Camada de regras de negocio e orquestracao.
* **[Helpers](helpers.md)**: Validacoes de dominio e suporte aos Services.
* **[Validators](validators.md)**: Validacoes atomicas de variaveis e campos.
* **[Models](models.md)**: Entidades de dominio e persistencia.
* **[DTOs](dtos.md)**: Objetos de transferencia de dados.
* **[Repositories](repositories.md)**: Interface de comunicacao com o banco de dados.
* **[Specifications](specifications.md)**: Queries dinamicas e reutilizaveis.
***[Configurations](configurations.md)**: Configuracoes de infraestrutura e segurança.
* **[Enums](enums.md)**: Constantes tipadas de dominio.
* **[Exceptions](exceptions.md)**: Padronizacao de erros e excecoes.
* **[Mappers](mappers.md)**: Conversao entre Entidades e DTOs.
* **[VOs](vos.md)**: Objetos de valor imutaveis.

## Principios Aplicados

1. **Independencia**: Camadas externas dependem das internas.
2. **Encapsulamento**: Logica de negocio protegida em Services.
3. **Padronizacao**: Nomenclatura clara e responsabilidades bem definidas.
