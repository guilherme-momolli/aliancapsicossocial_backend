package br.com.aliancapsicossocial.dtos.faq;

import br.com.aliancapsicossocial.enums.faq.FAQCategory;
import java.util.UUID;

public record FAQItemResponseDTO(
    UUID id,
    String pergunta,
    String resposta,
    FAQCategory categoria,
    Integer ordem,
    boolean ativo
) {
}
