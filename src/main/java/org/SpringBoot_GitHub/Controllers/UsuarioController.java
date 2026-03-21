package org.SpringBoot_GitHub.Controllers;

import org.SpringBoot_GitHub.Docs.ProblemResponse;
import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.UsuarioDTO;
import org.SpringBoot_GitHub.Services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Lista todos os usuários cadastrados")
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @Operation(summary = "Busca um usuário específico pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProblemResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RecursosNaoEncontradosException("Usuário não encontrado com o ID: " + id));
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Cadastra um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Missão vinculada não encontrada", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProblemResponse.class))),
        @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(implementation = ProblemResponse.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO novoUsuario = usuarioService.salvar(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @Operation(summary = "Deleta um usuário pelo seu ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}