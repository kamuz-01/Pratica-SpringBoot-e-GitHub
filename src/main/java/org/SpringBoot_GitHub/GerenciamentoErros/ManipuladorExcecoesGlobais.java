package org.SpringBoot_GitHub.GerenciamentoErros;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class ManipuladorExcecoesGlobais {

    private static final Logger log =
            LoggerFactory.getLogger(ManipuladorExcecoesGlobais.class);

    private ProblemDetail criarProblemDetail(
            HttpStatus status,
            String title,
            String detail,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(status);

        problem.setTitle(title);
        problem.setDetail(detail);
        problem.setInstance(URI.create(request.getRequestURI()));

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.of("pt", "BR"));

        problem.setProperty("timestamp", LocalDateTime.now().format(formatter));
        problem.setProperty("method", request.getMethod());

        return problem;
    }

    // 400 - Parâmetro com tipo errado na URL (Ex: "w" no lugar de um número)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tratarTipoArgumentoInvalido(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        log.warn("Tipo de argumento inválido em {} : {}", request.getRequestURI(), ex.getMessage());

        String valorEnviado = ex.getValue() != null ? ex.getValue().toString() : "nulo";
        String tipoEsperado = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";

        String detalhe = String.format("O parâmetro '%s' recebeu o valor '%s', que é de um tipo inválido. O tipo correto deve ser '%s'.",
                ex.getName(), valorEnviado, tipoEsperado);

        return criarProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Parâmetro de URL inválido",
                detalhe,
                request);
    }

    // 404
    @ExceptionHandler(RecursosNaoEncontradosException.class)
    public ProblemDetail tratarNaoEncontrado(
            RecursosNaoEncontradosException ex,
            HttpServletRequest request) {

        log.warn("Recurso não encontrado: {} - {}", request.getRequestURI(), ex.getMessage());

        return criarProblemDetail(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                ex.getMessage(),
                request);
    }

    // 400 validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail tratarErroValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        log.warn("Erro de validação em {} : {}", request.getRequestURI(), ex.getMessage());

        ProblemDetail problem = criarProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                "Um ou mais campos estão inválidos",
                request);

        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        problem.setProperty("errors", erros);

        return problem;
    }

    // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail tratarConflito(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        log.error("Conflito de dados em {} : {}", request.getRequestURI(), ex.getMessage());

        return criarProblemDetail(
                HttpStatus.CONFLICT,
                "Conflito de dados",
                "Violação de integridade no banco de dados",
                request);
    }

    // 422
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail tratarRegraNegocio(
            IllegalStateException ex,
            HttpServletRequest request) {

        log.warn("Erro de regra de negócio em {} : {}", request.getRequestURI(), ex.getMessage());

        return criarProblemDetail(
                HttpStatus.UNPROCESSABLE_CONTENT,
                "Erro de regra de negócio",
                ex.getMessage(),
                request);
    }

    // 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail tratarBadRequest(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        log.warn("Requisição inválida em {} : {}", request.getRequestURI(), ex.getMessage());

        return criarProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Requisição inválida",
                ex.getMessage(),
                request);
    }

    // 500
    @ExceptionHandler(Exception.class)
    public ProblemDetail tratarErroInterno(
            Exception ex,
            HttpServletRequest request) {

        log.error("Erro interno em {} ", request.getRequestURI(), ex);

        return criarProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno do servidor",
                "Ocorreu um erro inesperado",
                request);
    }
}