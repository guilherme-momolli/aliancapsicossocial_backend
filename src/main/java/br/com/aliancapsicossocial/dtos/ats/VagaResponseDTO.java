package br.com.aliancapsicossocial.dtos.ats;

import br.com.aliancapsicossocial.enums.ats.VagaStatus;
import java.time.LocalDate;
import java.util.UUID;

public record VagaResponseDTO(
    UUID id,
    String titulo,
    String descricao,
    String requisitos,
    LocalDate dataLimite,
    VagaStatus status
) {}
