package br.com.aliancapsicossocial.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResponseDTO(
    Instant timestamp,
    Integer status,
    String error,
    String message,
    String path,
    List<ErroCampoDTO> errors
) {
    public ErroResponseDTO(Integer status, String error, String message, String path) {
        this(Instant.now(), status, error, message, path, null);
    }

    public ErroResponseDTO(Integer status, String error, String message, String path, List<ErroCampoDTO> errors) {
        this(Instant.now(), status, error, message, path, errors);
    }
}
