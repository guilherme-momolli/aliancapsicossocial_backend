# Camada de Mappers (MapStruct)

A camada de Mappers e responsavel por realizar a conversao entre Entidades de Dominio (Models) e Objetos de Transferencia de Dados (DTOs). No projeto, utilizamos o **MapStruct** para automatizar esse processo de forma performatica e segura.

## Por que MapStruct?

1. **Performance**: Diferente de bibliotecas que usam Reflection (como ModelMapper), o MapStruct gera codigo Java nativo durante a compilacao.
2. **Type-Safety**: Erros de mapeamento sao detectados durante a compilacao, nao em tempo de execucao.
3. **Clean Code**: Remove o codigo repetitivo de "getters e setters" manuais dos Services e Controllers.

## Dependencias Necessarias

No `pom.xml`, as seguintes dependencias e configuracoes sao requeridas:

```xml
<properties>
    <org.mapstruct.version>1.6.3</org.mapstruct.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct</artifactId>
        <version>${org.mapstruct.version}</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                    </path>
                    <path>
                        <groupId>org.mapstruct</groupId>
                        <artifactId>mapstruct-processor</artifactId>
                        <version>${org.mapstruct.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Padroes de Implementacao

1. **Interface**: Mappers devem ser interfaces anotadas com `@Mapper(componentModel = "spring")`.
2. **Nomenclatura**: Devem terminar com o sufixo `Mapper` (ex: `UsuarioMapper`).
3. **Metodos Padrao**:
   - `toEntity`: Converte DTO para Entity.
   - `toDTO`: Converte Entity para DTO.
   - `updateEntity`: Atualiza uma entidade existente a partir de um DTO.

## Exemplo de Uso

```java
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(RegisterRequestDTO dto);

    LoginResponseDTO toDTO(Usuario entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(RegisterRequestDTO dto, @MappingTarget Usuario entity);
}
```

## Boas Praticas

- **Injeçao**: Sempre utilize a injeçao de dependencia do Spring para usar o mapper nos Services.
- **Tratamento de Nulos**: O MapStruct lida com nulos por padrao, mas comportamentos especificos podem ser configurados.
- **Mapeamentos Complexos**: Use a anotacao `@Mapping` para lidar com nomes de campos diferentes ou formatacoes.
