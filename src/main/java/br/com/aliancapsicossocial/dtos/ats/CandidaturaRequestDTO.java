package br.com.aliancapsicossocial.dtos.ats;

import br.com.aliancapsicossocial.enums.ats.CandidaturaStatus;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CandidaturaRequestDTO(
    @NotNull(message = "A vaga é obrigatória")
    UUID vagaId,

    @NotNull(message = "A pessoa é obrigatória")
    UUID pessoaId,

    @NotNull(message = "O status é obrigatório")
    CandidaturaStatus status,

    String observacoes
) {}
