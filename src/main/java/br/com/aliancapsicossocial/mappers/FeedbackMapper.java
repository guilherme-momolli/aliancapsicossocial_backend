package br.com.aliancapsicossocial.mappers;

import br.com.aliancapsicossocial.dtos.feedback.FeedbackRequestDTO;
import br.com.aliancapsicossocial.dtos.feedback.FeedbackResponseDTO;
import br.com.aliancapsicossocial.models.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "respostaSuporte", ignore = true)
    @Mapping(target = "respondidoPor", ignore = true)
    Feedback toEntity(FeedbackRequestDTO dto);

    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "usuarioEmail", source = "usuario.email")
    @Mapping(target = "respondidoPorId", source = "respondidoPor.id")
    FeedbackResponseDTO toDTO(Feedback entity);
}
