package org.SpringBoot_GitHub.Services;

import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.MissoesDTO;
import org.SpringBoot_GitHub.Models.Entities.Missoes;
import org.SpringBoot_GitHub.Repositories.MissoesRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MissoesService {

    private final MissoesRepository missoesRepository;

    public MissoesService(MissoesRepository missoesRepository) {
        this.missoesRepository = missoesRepository;
    }

    public List<MissoesDTO> listarTodas() {
        return missoesRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<MissoesDTO> buscarPorId(Long id) {
        return missoesRepository.findById(id).map(this::converterParaDTO);
    }

    public MissoesDTO salvar(MissoesDTO missaoDTO) {
        Missoes missao = converterParaEntidade(missaoDTO);
        Missoes missaoSalva = missoesRepository.save(missao);
        return converterParaDTO(missaoSalva);
    }

    // --- Método de atualização ---
    public MissoesDTO atualizar(Long id, MissoesDTO missaoDTO) {
        Missoes missaoExistente = missoesRepository.findById(id)
                .orElseThrow(() -> new RecursosNaoEncontradosException("Missão não encontrada com o ID: " + id));

        missaoExistente.setNomeMissao(missaoDTO.getNomeMissao());
        missaoExistente.setDescricao(missaoDTO.getDescricao());
        missaoExistente.setGrauDificuldade(missaoDTO.getGrauDificuldade());
        missaoExistente.setStatusMissao(missaoDTO.getStatusMissao());

        Missoes missaoAtualizada = missoesRepository.save(missaoExistente);
        return converterParaDTO(missaoAtualizada);
    }

    // --- Verifica se o ID existe antes de deletar ---
    public void deletar(Long id) {
        if (!missoesRepository.existsById(id)) {
            throw new RecursosNaoEncontradosException("Missão não encontrada com o ID: " + id);
        }
        missoesRepository.deleteById(id);
    }

    // --- MÉTODOS AUXILIARES DE CONVERSÃO ---

    private MissoesDTO converterParaDTO(Missoes missao) {
        MissoesDTO dto = new MissoesDTO();
        dto.setIdMissao(missao.getIdMissao());
        dto.setNomeMissao(missao.getNomeMissao());
        dto.setDescricao(missao.getDescricao());
        dto.setGrauDificuldade(missao.getGrauDificuldade());
        dto.setStatusMissao(missao.getStatusMissao());
        return dto;
    }

    private Missoes converterParaEntidade(MissoesDTO dto) {
        Missoes missao = new Missoes();
        missao.setNomeMissao(dto.getNomeMissao());
        missao.setDescricao(dto.getDescricao());
        missao.setGrauDificuldade(dto.getGrauDificuldade());
        missao.setStatusMissao(dto.getStatusMissao());
        return missao;
    }
}