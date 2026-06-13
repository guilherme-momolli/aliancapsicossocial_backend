package br.com.aliancapsicossocial.helpers;

import br.com.aliancapsicossocial.exceptions.RegraDeNegocioException;
import br.com.aliancapsicossocial.models.Pessoa;
import br.com.aliancapsicossocial.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PessoaHelper {

    private final PessoaRepository repository;

    public void validarCriacao(Pessoa pessoa) {
        if (repository.existsByCpf(pessoa.getCpf())) {
            throw new RegraDeNegocioException("CPF já cadastrado.");
        }
    }

    public void validarAtualizacao(UUID id, Pessoa pessoa) {
        repository.findByCpf(pessoa.getCpf())
                .ifPresent(p -> {
                    if (!p.getId().equals(id)) {
                        throw new RegraDeNegocioException("CPF já cadastrado em outra pessoa.");
                    }
                });
    }
}
