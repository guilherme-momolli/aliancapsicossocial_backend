package br.com.aliancapsicossocial.dtos.feedback;

import java.util.Map;

public record FeedbackStatsDTO(
    long totalFeedbacks,
    double mediaNotas,
    Map<String, Long> porTipo,
    Map<String, Long> porStatus
) {
}
