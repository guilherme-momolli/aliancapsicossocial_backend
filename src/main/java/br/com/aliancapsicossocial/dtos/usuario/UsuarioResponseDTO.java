package br.com.aliancapsicossocial.dtos.usuario;

import br.com.aliancapsicossocial.enums.usuario.UsuarioRole;
import java.util.UUID;

public record UsuarioResponseDTO(
    UUID id,
    String email,
    UsuarioRole role
) {}
