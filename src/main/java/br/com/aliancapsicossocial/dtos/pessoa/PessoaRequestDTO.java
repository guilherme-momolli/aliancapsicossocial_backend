package br.com.aliancapsicossocial.dtos.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PessoaRequestDTO(
    String fotoPerfil,

    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotNull(message = "A data de nascimento é obrigatória")
    LocalDate dataNascimento,

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido")
    String cpf,

    @Size(max = 20)
    String rg,

    @NotNull(message = "O endereço é obrigatório")
    EnderecoRequestDTO endereco
) {}
