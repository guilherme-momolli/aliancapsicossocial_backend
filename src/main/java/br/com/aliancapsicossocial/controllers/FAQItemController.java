package br.com.aliancapsicossocial.controllers;

import br.com.aliancapsicossocial.dtos.faq.FAQItemRequestDTO;
import br.com.aliancapsicossocial.dtos.faq.FAQItemResponseDTO;
import br.com.aliancapsicossocial.services.FAQItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FAQItemController {

    private final FAQItemService service;

    @GetMapping
    public ResponseEntity<List<FAQItemResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQItemResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPORTE')")
    public ResponseEntity<FAQItemResponseDTO> criar(@RequestBody @Valid FAQItemRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPORTE')")
    public ResponseEntity<FAQItemResponseDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid FAQItemRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPORTE')")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
