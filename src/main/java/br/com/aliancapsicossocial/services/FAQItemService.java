package br.com.aliancapsicossocial.services;

import br.com.aliancapsicossocial.dtos.faq.FAQItemRequestDTO;
import br.com.aliancapsicossocial.dtos.faq.FAQItemResponseDTO;
import br.com.aliancapsicossocial.exceptions.RecursoNaoEncontradoException;
import br.com.aliancapsicossocial.mappers.FAQItemMapper;
import br.com.aliancapsicossocial.models.FAQItem;
import br.com.aliancapsicossocial.repositories.FAQItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQItemService {

    private final FAQItemRepository repository;
    private final FAQItemMapper mapper;

    @Transactional(readOnly = true)
    public List<FAQItemResponseDTO> listar() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FAQItemResponseDTO buscarPorId(UUID id) {
        return mapper.toDTO(buscarEntidadePorId(id));
    }

    @Transactional
    public FAQItemResponseDTO criar(FAQItemRequestDTO dto) {
        FAQItem entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public FAQItemResponseDTO atualizar(UUID id, FAQItemRequestDTO dto) {
        FAQItem entity = buscarEntidadePorId(id);
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public void excluir(UUID id) {
        FAQItem entity = buscarEntidadePorId(id);
        repository.delete(entity);
    }

    private FAQItem buscarEntidadePorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("FAQ não encontrado com o ID: " + id));
    }
}
