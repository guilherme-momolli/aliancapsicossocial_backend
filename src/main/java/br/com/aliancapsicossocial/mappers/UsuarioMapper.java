package br.com.aliancapsicossocial.mappers;

import br.com.aliancapsicossocial.dtos.auth.UsuarioResponseDTO;
import br.com.aliancapsicossocial.models.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getRole(),
            usuario.isTwoFactorEnabled()
        );
    }
}
