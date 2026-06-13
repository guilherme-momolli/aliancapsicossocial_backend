package br.com.aliancapsicossocial.dtos.auth;

import br.com.aliancapsicossocial.enums.usuario.UsuarioRole;
import java.util.UUID;

public record UsuarioResponseDTO(
    UUID id,
    String email,
    UsuarioRole role,
    boolean twoFactorEnabled
) {}
