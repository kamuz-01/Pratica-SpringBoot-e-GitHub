package org.SpringBoot_GitHub.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.UsuarioDTO;
import org.SpringBoot_GitHub.Services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void prepararDados() {
        // Usando um CPF matematicamente válido para não falhar na validação do Spring
        usuarioDTO = new UsuarioDTO(1L, "João", "Silva", "858.795.130-09", LocalDate.of(1990, 5, 15), "11999999999", "joao@email.com", 1L);
    }

    @Test
    @DisplayName("GET /api/v1/usuarios - Deve retornar lista de usuários e status 200")
    void listarTodosDeveRetornarStatus200() throws Exception {
        when(usuarioService.listarTodos()).thenReturn(List.of(usuarioDTO));

        mockMvc.perform(get("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[0].cpf").value("858.795.130-09"));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} - Deve retornar usuário e status 200")
    void buscarPorIdDeveRetornarStatus200() throws Exception {
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuarioDTO));

        mockMvc.perform(get("/api/v1/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} - Deve retornar 404 quando ID não existir")
    void buscarPorIdDeveRetornarStatus404() throws Exception {
        when(usuarioService.buscarPorId(99L)).thenThrow(new RecursosNaoEncontradosException("Usuário não encontrado com o ID: 99"));

        mockMvc.perform(get("/api/v1/usuarios/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.titulo").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.detalhe").value("Usuário não encontrado com o ID: 99"));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios - Deve criar usuário e retornar status 201")
    void criarDeveRetornarStatus201() throws Exception {
        when(usuarioService.salvar(any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1L))
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios - Deve falhar na validação (CPF inválido) e retornar 400")
    void criarComDadosInvalidosDeveRetornarStatus400() throws Exception {
        // CPF inventado ("11111111111") para forçar o Erro 400 do @CPF
        UsuarioDTO dtoInvalido = new UsuarioDTO(null, "Maria", "Souza", "11111111111", LocalDate.of(1995, 2, 20), "11988888888", "maria@email.com", 1L);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.titulo").value("Erro de validação"))
                .andExpect(jsonPath("$.detalhe").value("Um ou mais campos estão inválidos: cpf: Formato de CPF inválido"));
    }

    @Test
    @DisplayName("PUT /api/v1/usuarios/{id} - Deve atualizar usuário e retornar status 200")
    void atualizarDeveRetornarStatus200() throws Exception {
        when(usuarioService.atualizar(eq(1L), any(UsuarioDTO.class))).thenReturn(usuarioDTO);

        mockMvc.perform(put("/api/v1/usuarios/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @DisplayName("DELETE /api/v1/usuarios/{id} - Deve deletar e retornar status 204")
    void deletarDeveRetornarStatus204() throws Exception {
        doNothing().when(usuarioService).deletar(1L);

        mockMvc.perform(delete("/api/v1/usuarios/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}