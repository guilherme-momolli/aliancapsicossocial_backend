package br.com.aliancapsicossocial.exceptions;

public record ErroCampoDTO(
    String campo,
    String mensagem
) {}
