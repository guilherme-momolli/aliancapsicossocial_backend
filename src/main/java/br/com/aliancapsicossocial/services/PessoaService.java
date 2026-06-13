package br.com.aliancapsicossocial.services;

import br.com.aliancapsicossocial.dtos.pessoa.PessoaRequestDTO;
import br.com.aliancapsicossocial.dtos.pessoa.PessoaResponseDTO;
import br.com.aliancapsicossocial.exceptions.RecursoNaoEncontradoException;
import br.com.aliancapsicossocial.helpers.PessoaHelper;
import br.com.aliancapsicossocial.mappers.PessoaMapper;
import br.com.aliancapsicossocial.models.Pessoa;
import br.com.aliancapsicossocial.repositories.PessoaRepository;
import br.com.aliancapsicossocial.specifications.PessoaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;
    private final PessoaHelper helper;

    @Transactional(readOnly = true)
    public Page<PessoaResponseDTO> listar(String nome, String cpf, Pageable pageable) {
        Specification<Pessoa> spec = Specification.where(PessoaSpecification.comNome(nome))
                .and(PessoaSpecification.comCpf(cpf));
        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public PessoaResponseDTO buscarPorId(UUID id) {
        return mapper.toDTO(buscarEntidadePorId(id));
    }

    @Transactional
    public PessoaResponseDTO criar(PessoaRequestDTO dto) {
        Pessoa pessoa = mapper.toEntity(dto);
        helper.validarCriacao(pessoa);
        return mapper.toDTO(repository.save(pessoa));
    }

    @Transactional
    public PessoaResponseDTO atualizar(UUID id, PessoaRequestDTO dto) {
        Pessoa pessoa = buscarEntidadePorId(id);
        mapper.updateEntityFromDto(dto, pessoa);
        helper.validarAtualizacao(id, pessoa);
        return mapper.toDTO(repository.save(pessoa));
    }

    @Transactional
    public void excluir(UUID id) {
        Pessoa pessoa = buscarEntidadePorId(id);
        repository.delete(pessoa);
    }

    private Pessoa buscarEntidadePorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa não encontrada com o ID: " + id));
    }
}
