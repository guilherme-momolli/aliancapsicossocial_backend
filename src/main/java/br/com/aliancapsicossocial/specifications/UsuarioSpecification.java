package br.com.aliancapsicossocial.specifications;

import br.com.aliancapsicossocial.enums.usuario.UsuarioRole;
import br.com.aliancapsicossocial.models.Usuario;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {

    public static Specification<Usuario> hasEmail(String email) {
        return (root, query, cb) -> email == null ? null : cb.like(root.get("email"), "%" + email + "%");
    }

    public static Specification<Usuario> hasRole(UsuarioRole role) {
        return (root, query, cb) -> role == null ? null : cb.equal(root.get("role"), role);
    }
    
    public static Specification<Usuario> isAtivo(Boolean ativo) {
        return (root, query, cb) -> ativo == null ? null : cb.equal(root.get("ativo"), ativo);
    }
}
