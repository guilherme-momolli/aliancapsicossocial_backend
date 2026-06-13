package br.com.aliancapsicossocial.enums.ats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CandidaturaStatus {
    TRIAGEM("Triagem"),
    ENTREVISTA_RH("Entrevista RH"),
    ENTREVISTA_TECNICA("Entrevista Técnica"),
    TESTE_PRATICO("Teste Prático"),
    PROPOSTA("Proposta"),
    CONTRATADO("Contratado"),
    REPROVADO("Reprovado"),
    DESISTENCIA("Desistência");

    private final String descricao;
}
