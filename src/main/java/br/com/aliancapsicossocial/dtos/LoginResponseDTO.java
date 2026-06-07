package br.com.aliancapsicossocial.dtos;

public record LoginResponseDTO(
    String token,
    String email,
    String role
) {}
