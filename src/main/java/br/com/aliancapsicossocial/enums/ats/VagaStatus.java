package br.com.aliancapsicossocial.enums.ats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VagaStatus {
    RASCUNHO("Rascunho"),
    ABERTA("Aberta"),
    PAUSADA("Pausada"),
    ENCERRADA("Encerrada"),
    CANCELADA("Cancelada");

    private final String descricao;
}
