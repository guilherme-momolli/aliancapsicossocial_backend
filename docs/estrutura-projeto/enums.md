# Camada de Enums

Define constantes tipadas que representam dominios fixos de dados na aplicacao.

## Responsabilidades

1. **Padronizacao**: Garantir que valores como status, tipos de usuario ou roles sejam consistentes em todo o sistema.
2. **Mapeamento JPA**: Facilitar a persistencia de strings ou ordinais no banco de dados com segurança de tipos.

## Boas Praticas

1. **Valores Descritivos**: Utilize campos internos para armazenar descricoes ou codigos amigaveis.
2. **Localizacao**: Devem ser organizados por dominio (ex: `enums/usuario/UsuarioRole.java`).
3. **Nomenclatura**: Devem ser claros e singulares (ex: `UsuarioRole`, `StatusPedido`).
