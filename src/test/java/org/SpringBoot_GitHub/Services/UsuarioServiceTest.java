package org.SpringBoot_GitHub.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Optional;
import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.UsuarioDTO;
import org.SpringBoot_GitHub.Models.Entities.Missoes;
import org.SpringBoot_GitHub.Models.Entities.Usuario;
import org.SpringBoot_GitHub.Models.Enums.GrauDificuldade;
import org.SpringBoot_GitHub.Models.Enums.StatusMissao;
import org.SpringBoot_GitHub.Repositories.MissoesRepository;
import org.SpringBoot_GitHub.Repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    // Precisamos de DUAS dependências mockadas agora!
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private MissoesRepository missoesRepository;

    private Usuario usuarioEntidade;
    private UsuarioDTO usuarioDTO;
    private Missoes missaoEntidade;

    @BeforeEach
    void prepararDados() {
        // Preparamos a missão primeiro, pois o usuário depende dela
        missaoEntidade = new Missoes(1L, "Missão Teste", "Desc", GrauDificuldade.FACIL, StatusMissao.PENDENTE, null);
        
        usuarioEntidade = new Usuario(1L, "João", "Silva", "12345678900", LocalDate.of(1990, 1, 1), "11999999999", "joao@email.com", missaoEntidade);
        
        usuarioDTO = new UsuarioDTO(1L, "João", "Silva", "12345678900", LocalDate.of(1990, 1, 1), "11999999999", "joao@email.com", 1L);
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso quando a missão existir")
    void salvarUsuarioComSucesso() {
        // GIVEN: O repositório de missões ACHA a missão, e o repositório de usuários SALVA o usuário
        when(missoesRepository.findById(1L)).thenReturn(Optional.of(missaoEntidade));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEntidade);

        // WHEN
        UsuarioDTO resultado = usuarioService.salvar(usuarioDTO);

        // THEN
        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        assertEquals(1L, resultado.getIdMissao());
        
        // Verificamos se ambos os repositórios foram chamados
        verify(missoesRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar usuário com missão inexistente")
    void salvarUsuarioDeveLancarExcecaoQuandoMissaoNaoExistir() {
        // GIVEN: O repositório de missões NÃO ACHA a missão
        when(missoesRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN & THEN
        RecursosNaoEncontradosException excecao = assertThrows(RecursosNaoEncontradosException.class, () -> {
            usuarioService.salvar(usuarioDTO);
        });

        assertEquals("Missão não encontrada com o ID: 1", excecao.getMessage());
        
        // Garantimos que o usuário NUNCA chegou a ser salvo no banco
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void buscarPorIdComSucesso() {
        // GIVEN
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntidade));

        // WHEN
        Optional<UsuarioDTO> resultado = usuarioService.buscarPorId(1L);

        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("João", resultado.get().getNome());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso quando ID existir")
    void deletarComSucesso() {
        // GIVEN
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);

        // WHEN
        assertDoesNotThrow(() -> usuarioService.deletar(1L));

        // THEN
        verify(usuarioRepository, times(1)).existsById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar usuário com ID inexistente")
    void deletarDeveLancarExcecaoQuandoIdNaoExistir() {
        // GIVEN
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        // WHEN & THEN
        RecursosNaoEncontradosException excecao = assertThrows(RecursosNaoEncontradosException.class, () -> {
            usuarioService.deletar(99L);
        });

        assertEquals("Usuário não encontrado com o ID: 99", excecao.getMessage());
        verify(usuarioRepository, never()).deleteById(anyLong());
    }
}