package br.com.aliancapsicossocial.dtos.pessoa;

import java.util.UUID;

public record EnderecoResponseDTO(
    UUID id,
    String logradouro,
    String cep,
    String bairro,
    String municipio,
    String estado,
    String pais
) {}
