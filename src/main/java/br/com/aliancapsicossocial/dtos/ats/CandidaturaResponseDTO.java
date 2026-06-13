package br.com.aliancapsicossocial.dtos.ats;

import br.com.aliancapsicossocial.enums.ats.CandidaturaStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record CandidaturaResponseDTO(
    UUID id,
    UUID vagaId,
    String vagaTitulo,
    UUID pessoaId,
    String pessoaNome,
    CandidaturaStatus status,
    LocalDateTime dataCandidatura,
    String observacoes
) {}
