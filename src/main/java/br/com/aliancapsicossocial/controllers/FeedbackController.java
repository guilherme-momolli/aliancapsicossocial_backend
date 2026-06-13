package br.com.aliancapsicossocial.controllers;

import br.com.aliancapsicossocial.dtos.feedback.*;
import br.com.aliancapsicossocial.models.Usuario;
import br.com.aliancapsicossocial.services.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService service;

    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> enviar(
            @RequestBody @Valid FeedbackRequestDTO dto,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enviar(dto, usuario));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPORTE')")
    public ResponseEntity<Page<FeedbackResponseDTO>> listarTodos(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPORTE')")
    public ResponseEntity<FeedbackResponseDTO> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody @Valid FeedbackStatusUpdateRequestDTO dto,
            @AuthenticationPrincipal Usuario respondidoPor) {
        return ResponseEntity.ok(service.atualizarStatus(id, dto, respondidoPor));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<FeedbackStatsDTO> obterEstatisticas() {
        return ResponseEntity.ok(service.obterEstatisticas());
    }
}
