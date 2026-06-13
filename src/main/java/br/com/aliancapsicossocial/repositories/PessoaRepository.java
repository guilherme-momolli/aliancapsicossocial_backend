package br.com.aliancapsicossocial.repositories;

import br.com.aliancapsicossocial.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID>, JpaSpecificationExecutor<Pessoa> {
    boolean existsByCpf(String cpf);
    Optional<Pessoa> findByCpf(String cpf);
}
