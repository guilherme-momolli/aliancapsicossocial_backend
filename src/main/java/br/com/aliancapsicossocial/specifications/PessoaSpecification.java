package br.com.aliancapsicossocial.specifications;

import br.com.aliancapsicossocial.models.Pessoa;
import org.springframework.data.jpa.domain.Specification;

public class PessoaSpecification {

    public static Specification<Pessoa> comNome(String nome) {
        return (root, query, cb) -> nome == null ? null : cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Pessoa> comCpf(String cpf) {
        return (root, query, cb) -> cpf == null ? null : cb.equal(root.get("cpf"), cpf);
    }
}
