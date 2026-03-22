package org.SpringBoot_GitHub.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import java.util.Optional;
import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.MissoesDTO;
import org.SpringBoot_GitHub.Models.Entities.Missoes;
import org.SpringBoot_GitHub.Models.Enums.GrauDificuldade;
import org.SpringBoot_GitHub.Models.Enums.StatusMissao;
import org.SpringBoot_GitHub.Repositories.MissoesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Esta anotação diz ao JUnit para usar o Mockito neste teste
@ExtendWith(MockitoExtension.class)
class MissoesServiceTest {

    // @InjectMocks injeta os dublês (Mocks) dentro da classe real que queremos testar
    @InjectMocks
    private MissoesService missoesService;

    // @Mock cria um "dublê" do repositório. Ele não vai ao banco de dados!
    @Mock
    private MissoesRepository missoesRepository;

    private Missoes missaoEntidade;
    private MissoesDTO missaoDTO;

    // O @BeforeEach roda sempre ANTES de cada teste para preparar os nossos dados
    @BeforeEach
    void prepararDados() {
        missaoEntidade = new Missoes(1L, "Aprender Testes", "Estudar JUnit e Mockito", GrauDificuldade.MEDIO, StatusMissao.EM_ANDAMENTO, null);
        missaoDTO = new MissoesDTO(1L, "Aprender Testes", "Estudar JUnit e Mockito", GrauDificuldade.MEDIO, StatusMissao.EM_ANDAMENTO);
    }

    @Test
    @DisplayName("Deve salvar uma missão com sucesso e retornar um DTO")
    void salvarMissaoComSucesso() {
        // GIVEN (Dado que o repositório é chamado, retorne a nossa missão simulada)
        when(missoesRepository.save(any(Missoes.class))).thenReturn(missaoEntidade);

        // WHEN (Quando chamamos o método do nosso service)
        MissoesDTO resultado = missoesService.salvar(missaoDTO);

        // THEN (Então o resultado não deve ser nulo e os dados devem bater)
        assertNotNull(resultado);
        assertEquals("Aprender Testes", resultado.getNomeMissao());
        assertEquals(GrauDificuldade.MEDIO, resultado.getGrauDificuldade());

        // Verifica se o método save() do repositório foi chamado exatamente 1 vez
        verify(missoesRepository, times(1)).save(any(Missoes.class));
    }

    @Test
    @DisplayName("Deve retornar um Optional contendo a missão ao buscar por um ID existente")
    void buscarPorIdComSucesso() {
        // GIVEN
        when(missoesRepository.findById(1L)).thenReturn(Optional.of(missaoEntidade));

        // WHEN
        Optional<MissoesDTO> resultado = missoesService.buscarPorId(1L);

        // THEN
        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdMissao());
        verify(missoesRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve deletar com sucesso quando o ID existir")
    void deletarComSucesso() {
        // GIVEN (Simulamos que o ID 1 existe no banco)
        when(missoesRepository.existsById(1L)).thenReturn(true);
        // O método deleteById retorna void (nada), então usamos doNothing()
        doNothing().when(missoesRepository).deleteById(1L);

        // WHEN
        // Como o método não retorna nada, se não lançar erro, o teste passou
        assertDoesNotThrow(() -> missoesService.deletar(1L));

        // THEN
        verify(missoesRepository, times(1)).existsById(1L);
        verify(missoesRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar RecursosNaoEncontradosException ao tentar deletar ID inexistente")
    void deletarDeveLancarExcecaoQuandoIdNaoExistir() {
        // GIVEN (Simulamos que o ID 99 não existe)
        when(missoesRepository.existsById(99L)).thenReturn(false);

        // WHEN & THEN (Verificamos se a exceção exata foi lançada)
        RecursosNaoEncontradosException excecao = assertThrows(RecursosNaoEncontradosException.class, () -> {
            missoesService.deletar(99L);
        });

        assertEquals("Missão não encontrada com o ID: 99", excecao.getMessage());

        // Verificamos que o deleteById NUNCA foi chamado, porque estourou erro antes
        verify(missoesRepository, never()).deleteById(anyLong());
    }
}