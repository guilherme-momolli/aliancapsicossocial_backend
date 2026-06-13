package br.com.aliancapsicossocial.dtos.faq;

import br.com.aliancapsicossocial.enums.faq.FAQCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FAQItemRequestDTO(
    @NotBlank String pergunta,
    @NotBlank String resposta,
    @NotNull FAQCategory categoria,
    @NotNull Integer ordem
) {
}
