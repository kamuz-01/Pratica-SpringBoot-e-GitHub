package org.SpringBoot_GitHub.GerenciamentoErros;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.SpringBoot_GitHub.Docs.ProblemResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ManipuladorExcecoesGlobais {

    private static final Logger log = LoggerFactory.getLogger(ManipuladorExcecoesGlobais.class);

    private ResponseEntity<ProblemResponse> criarProblemResponse(
            HttpStatus status,
            String titulo,
            String detalhe,
            HttpServletRequest request) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.of("pt", "BR"));

        // O @Builder do Lombok permite criar o objeto de forma muito elegante
        ProblemResponse problem = ProblemResponse.builder()
                .status(status.value())
                .titulo(titulo)
                .detalhe(detalhe)
                .instancia(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .metodo(request.getMethod())
                .build();

        return ResponseEntity.status(status).body(problem);
    }

    // 404
    @ExceptionHandler(RecursosNaoEncontradosException.class)
    public ResponseEntity<ProblemResponse> tratarNaoEncontrado(
            RecursosNaoEncontradosException ex,
            HttpServletRequest request) {

        log.warn("Recurso não encontrado: {} - {}", request.getRequestURI(), ex.getMessage());

        return criarProblemResponse(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                ex.getMessage(),
                request);
    }

    // 404 - Rota ou Recurso estático não encontrado (ex: favicon.ico)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ProblemResponse> tratarRotaNaoEncontrada(
            NoResourceFoundException ex,
            HttpServletRequest request) {

        log.warn("Rota ou recurso não encontrado: {}", request.getRequestURI());

        return criarProblemResponse(
                HttpStatus.NOT_FOUND,
                "Caminho não encontrado",
                "A URL ou recurso solicitado ('" + request.getRequestURI() + "') não existe neste servidor.",
                request);
    }

    // 400 - Parâmetro com tipo errado na URL (Ex: "w" ou "-" no lugar de um número)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemResponse> tratarTipoArgumentoInvalido(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        log.warn("Tipo de argumento inválido em {} : {}", request.getRequestURI(), ex.getMessage());

        String valorEnviado = ex.getValue() != null ? ex.getValue().toString() : "nulo";
        String tipoEsperado = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";

        String detalhe = String.format("O parâmetro '%s' recebeu o valor '%s', que é de um tipo inválido. O tipo correto deve ser '%s'.",
                ex.getName(), valorEnviado, tipoEsperado);

        return criarProblemResponse(
                HttpStatus.BAD_REQUEST,
                "Parâmetro de URL inválido",
                detalhe,
                request);
    }

    // 400 - Validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemResponse> tratarErroValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        log.warn("Erro de validação em {} : {}", request.getRequestURI(), ex.getMessage());

        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        String detalhe = "Um ou mais campos estão inválidos: " + String.join(", ", erros);

        return criarProblemResponse(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                detalhe,
                request);
    }

    // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemResponse> tratarConflito(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.error("Conflito de dados em {} : {}", request.getRequestURI(), ex.getMessage());

        return criarProblemResponse(
                HttpStatus.CONFLICT,
                "Conflito de dados",
                "Violação de integridade na base de dados",
                request);
    }

    // 422
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemResponse> tratarRegraNegocio(
            IllegalStateException ex,
            HttpServletRequest request) {

        log.warn("Erro de regra de negócio em {} : {}", request.getRequestURI(), ex.getMessage());

        return criarProblemResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Erro de regra de negócio",
                ex.getMessage(),
                request);
    }

    // 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemResponse> tratarBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        log.warn("Requisição inválida em {} : {}", request.getRequestURI(), ex.getMessage());

        return criarProblemResponse(
                HttpStatus.BAD_REQUEST,
                "Requisição inválida",
                ex.getMessage(),
                request);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemResponse> tratarErroInterno(
            Exception ex,
            HttpServletRequest request) {

        log.error("Erro interno em {} ", request.getRequestURI(), ex);

        return criarProblemResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno do servidor",
                "Ocorreu um erro inesperado",
                request);
    }
}