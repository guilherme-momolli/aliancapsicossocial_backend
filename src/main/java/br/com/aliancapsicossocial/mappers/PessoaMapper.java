package br.com.aliancapsicossocial.mappers;

import br.com.aliancapsicossocial.dtos.pessoa.PessoaRequestDTO;
import br.com.aliancapsicossocial.dtos.pessoa.PessoaResponseDTO;
import br.com.aliancapsicossocial.models.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    Pessoa toEntity(PessoaRequestDTO dto);

    PessoaResponseDTO toDTO(Pessoa entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntityFromDto(PessoaRequestDTO dto, @MappingTarget Pessoa entity);
}
