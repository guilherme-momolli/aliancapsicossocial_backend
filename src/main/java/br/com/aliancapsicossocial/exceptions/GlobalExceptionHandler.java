package br.com.aliancapsicossocial.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var errorResponse = new ErroResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponseDTO> handleRegraDeNegocio(RegraDeNegocioException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var errorResponse = new ErroResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        
        List<ErroCampoDTO> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErroCampoDTO(error.getField(), error.getDefaultMessage()))
                .toList();

        var errorResponse = new ErroResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                "Falha na validação dos dados de entrada.",
                request.getRequestURI(),
                fieldErrors
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado no servidor: ", ex);
        
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorResponse = new ErroResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                "Ocorreu um erro interno inesperado no servidor.",
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}
