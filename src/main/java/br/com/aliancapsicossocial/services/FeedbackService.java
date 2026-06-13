package br.com.aliancapsicossocial.services;

import br.com.aliancapsicossocial.dtos.feedback.*;
import br.com.aliancapsicossocial.enums.feedback.FeedbackStatus;
import br.com.aliancapsicossocial.exceptions.RecursoNaoEncontradoException;
import br.com.aliancapsicossocial.mappers.FeedbackMapper;
import br.com.aliancapsicossocial.models.Feedback;
import br.com.aliancapsicossocial.models.Usuario;
import br.com.aliancapsicossocial.repositories.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository repository;
    private final FeedbackMapper mapper;

    @Transactional
    public FeedbackResponseDTO enviar(FeedbackRequestDTO dto, Usuario usuario) {
        Feedback feedback = mapper.toEntity(dto);
        feedback.setUsuario(usuario);
        feedback.setStatus(FeedbackStatus.RECEBIDO);
        return mapper.toDTO(repository.save(feedback));
    }

    @Transactional(readOnly = true)
    public Page<FeedbackResponseDTO> listarTodos(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional
    public FeedbackResponseDTO atualizarStatus(UUID id, FeedbackStatusUpdateRequestDTO dto, Usuario respondidoPor) {
        Feedback feedback = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Feedback não encontrado com o ID: " + id));
        
        feedback.setStatus(dto.status());
        feedback.setRespostaSuporte(dto.respostaSuporte());
        feedback.setRespondidoPor(respondidoPor);
        
        return mapper.toDTO(repository.save(feedback));
    }

    @Transactional(readOnly = true)
    public FeedbackStatsDTO obterEstatisticas() {
        var feedbacks = repository.findAll();
        
        long total = feedbacks.size();
        double media = feedbacks.stream()
                .mapToInt(Feedback::getNota)
                .average()
                .orElse(0.0);
        
        var porTipo = feedbacks.stream()
                .collect(Collectors.groupingBy(f -> f.getTipo().name(), Collectors.counting()));
        
        var porStatus = feedbacks.stream()
                .collect(Collectors.groupingBy(f -> f.getStatus().name(), Collectors.counting()));
        
        return new FeedbackStatsDTO(total, media, porTipo, porStatus);
    }
}
