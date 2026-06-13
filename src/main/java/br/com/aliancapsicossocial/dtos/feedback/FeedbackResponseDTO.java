package br.com.aliancapsicossocial.dtos.feedback;

import br.com.aliancapsicossocial.enums.feedback.FeedbackStatus;
import br.com.aliancapsicossocial.enums.feedback.FeedbackType;
import java.time.LocalDateTime;
import java.util.UUID;

public record FeedbackResponseDTO(
    UUID id,
    UUID usuarioId,
    String usuarioEmail,
    FeedbackType tipo,
    String assunto,
    String mensagem,
    Integer nota,
    FeedbackStatus status,
    String respostaSuporte,
    UUID respondidoPorId,
    LocalDateTime dataCriacao
) {
}
