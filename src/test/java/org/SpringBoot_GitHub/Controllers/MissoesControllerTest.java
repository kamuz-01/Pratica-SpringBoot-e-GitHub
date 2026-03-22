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

import java.util.List;
import java.util.Optional;

import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.MissoesDTO;
import org.SpringBoot_GitHub.Models.Enums.GrauDificuldade;
import org.SpringBoot_GitHub.Models.Enums.StatusMissao;
import org.SpringBoot_GitHub.Services.MissoesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MissoesController.class)
class MissoesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MissoesService missoesService;

    private MissoesDTO missaoDTO;

    @BeforeEach
    void prepararDados() {
        missaoDTO = new MissoesDTO(1L, "Missão Lua", "Chegar na lua", GrauDificuldade.EXTREMO, StatusMissao.PENDENTE);
    }

    @Test
    @DisplayName("GET /api/v1/missoes - Deve retornar lista de missões e status 200")
    void listarTodasDeveRetornarStatus200() throws Exception {
        when(missoesService.listarTodas()).thenReturn(List.of(missaoDTO));

        mockMvc.perform(get("/api/v1/missoes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomeMissao").value("Missão Lua"))
                .andExpect(jsonPath("$[0].grauDificuldade").value("EXTREMO"));
    }

    @Test
    @DisplayName("GET /api/v1/missoes/{id} - Deve retornar missão e status 200")
    void buscarPorIdDeveRetornarStatus200() throws Exception {
        when(missoesService.buscarPorId(1L)).thenReturn(Optional.of(missaoDTO));

        mockMvc.perform(get("/api/v1/missoes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeMissao").value("Missão Lua"));
    }

    @Test
    @DisplayName("GET /api/v1/missoes/{id} - Deve retornar status 404 quando ID não existir")
    void buscarPorIdDeveRetornarStatus404() throws Exception {
        when(missoesService.buscarPorId(99L)).thenThrow(new RecursosNaoEncontradosException("Missão não encontrada com o ID: 99"));

        mockMvc.perform(get("/api/v1/missoes/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.titulo").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.detalhe").value("Missão não encontrada com o ID: 99"));
    }

    @Test
    @DisplayName("POST /api/v1/missoes - Deve criar missão e retornar status 201")
    void criarDeveRetornarStatus201() throws Exception {
        when(missoesService.salvar(any(MissoesDTO.class))).thenReturn(missaoDTO);

        mockMvc.perform(post("/api/v1/missoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(missaoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMissao").value(1L))
                .andExpect(jsonPath("$.nomeMissao").value("Missão Lua"));
    }

    @Test
    @DisplayName("POST /api/v1/missoes - Deve falhar na validação e retornar 400")
    void criarComDadosInvalidosDeveRetornarStatus400() throws Exception {
        MissoesDTO dtoInvalido = new MissoesDTO(null, "", "Desc", null, null);

        mockMvc.perform(post("/api/v1/missoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.titulo").value("Erro de validação"));
    }

    @Test
    @DisplayName("PUT /api/v1/missoes/{id} - Deve atualizar missão e retornar status 200")
    void atualizarDeveRetornarStatus200() throws Exception {
        when(missoesService.atualizar(eq(1L), any(MissoesDTO.class))).thenReturn(missaoDTO);

        mockMvc.perform(put("/api/v1/missoes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(missaoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeMissao").value("Missão Lua"));
    }

    @Test
    @DisplayName("DELETE /api/v1/missoes/{id} - Deve deletar e retornar status 204")
    void deletarDeveRetornarStatus204() throws Exception {
        doNothing().when(missoesService).deletar(1L);

        mockMvc.perform(delete("/api/v1/missoes/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}