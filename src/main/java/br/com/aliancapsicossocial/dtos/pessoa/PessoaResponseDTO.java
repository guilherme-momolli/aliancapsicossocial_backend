package br.com.aliancapsicossocial.dtos.pessoa;

import java.time.LocalDate;
import java.util.UUID;

public record PessoaResponseDTO(
    UUID id,
    String fotoPerfil,
    String nome,
    LocalDate dataNascimento,
    String cpf,
    String rg,
    EnderecoResponseDTO endereco
) {}
