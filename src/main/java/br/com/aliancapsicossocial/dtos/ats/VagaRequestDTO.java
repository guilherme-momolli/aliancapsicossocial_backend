package br.com.aliancapsicossocial.dtos.ats;

import br.com.aliancapsicossocial.enums.ats.VagaStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record VagaRequestDTO(
    @NotBlank(message = "O título é obrigatório")
    String titulo,

    @NotBlank(message = "A descrição é obrigatória")
    String descricao,

    @NotBlank(message = "Os requisitos são obrigatórios")
    String requisitos,

    LocalDate dataLimite,

    @NotNull(message = "O status é obrigatório")
    VagaStatus status
) {}
