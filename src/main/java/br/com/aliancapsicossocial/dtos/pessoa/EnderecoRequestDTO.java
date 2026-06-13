package br.com.aliancapsicossocial.dtos.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoRequestDTO(
    @NotBlank(message = "O logradouro é obrigatório")
    String logradouro,

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{8}|\\d{5}-\\d{3}", message = "CEP inválido")
    String cep,

    @NotBlank(message = "O bairro é obrigatório")
    String bairro,

    @NotBlank(message = "O município é obrigatório")
    String municipio,

    @NotBlank(message = "O estado é obrigatório")
    String estado,

    @NotBlank(message = "O país é obrigatório")
    String pais
) {}
