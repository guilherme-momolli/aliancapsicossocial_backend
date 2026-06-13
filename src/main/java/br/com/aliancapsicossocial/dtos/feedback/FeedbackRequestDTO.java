package br.com.aliancapsicossocial.dtos.feedback;

import br.com.aliancapsicossocial.enums.feedback.FeedbackType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequestDTO(
    @NotNull FeedbackType tipo,
    @NotBlank String assunto,
    @NotBlank String mensagem,
    @NotNull @Min(1) @Max(5) Integer nota
) {
}
