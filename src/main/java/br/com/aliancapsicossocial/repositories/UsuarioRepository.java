package br.com.aliancapsicossocial.repositories;

import br.com.aliancapsicossocial.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByPasswordResetToken(String token);
    Optional<Usuario> findByRefreshToken(String token);
}
