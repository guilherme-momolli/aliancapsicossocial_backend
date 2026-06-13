package br.com.aliancapsicossocial.dtos.feedback;

import br.com.aliancapsicossocial.enums.feedback.FeedbackStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackStatusUpdateRequestDTO(
    @NotNull FeedbackStatus status,
    @NotBlank String respostaSuporte
) {
}
