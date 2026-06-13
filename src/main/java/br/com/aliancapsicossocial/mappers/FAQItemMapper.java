package br.com.aliancapsicossocial.mappers;

import br.com.aliancapsicossocial.dtos.faq.FAQItemRequestDTO;
import br.com.aliancapsicossocial.dtos.faq.FAQItemResponseDTO;
import br.com.aliancapsicossocial.models.FAQItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FAQItemMapper {

    FAQItem toEntity(FAQItemRequestDTO dto);

    FAQItemResponseDTO toDTO(FAQItem entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(FAQItemRequestDTO dto, @MappingTarget FAQItem entity);
}
