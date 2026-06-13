package br.com.aliancapsicossocial.dtos.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenResponseDTO(
    String accessToken,
    String refreshToken,
    Boolean requires2FA
) {}
