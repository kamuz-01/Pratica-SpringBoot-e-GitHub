package org.SpringBoot_GitHub.Controllers;

import org.SpringBoot_GitHub.Docs.ProblemResponse;
import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.MissoesDTO;
import org.SpringBoot_GitHub.Services.MissoesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/missoes")
public class MissoesController {

    private final MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @Operation(summary = "Lista todas as missões disponíveis")
    @GetMapping
    public ResponseEntity<List<MissoesDTO>> listarTodas() {
        return ResponseEntity.ok(missoesService.listarTodas());
    }

    @Operation(summary = "Busca uma missão específica pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Missão encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Missão não encontrada", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MissoesDTO> buscarPorId(@PathVariable Long id) {
        MissoesDTO missao = missoesService.buscarPorId(id)
                .orElseThrow(() -> new RecursosNaoEncontradosException("Missão não encontrada com o ID: " + id));
        return ResponseEntity.ok(missao);
    }

    @Operation(summary = "Cadastra uma nova missão")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Missão criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MissoesDTO> criar(@Valid @RequestBody MissoesDTO missoesDTO) {
        MissoesDTO novaMissao = missoesService.salvar(missoesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMissao);
    }

    @Operation(summary = "Atualiza uma missão existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Missão atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Missão não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemResponse.class))),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MissoesDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MissoesDTO missoesDTO) {
        return ResponseEntity.ok(missoesService.atualizar(id, missoesDTO));
    }

    @Operation(summary = "Deleta uma missão pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Missão deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Missão não encontrada", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        missoesService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}