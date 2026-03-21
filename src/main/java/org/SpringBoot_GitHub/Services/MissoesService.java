package org.SpringBoot_GitHub.Services;

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

    public void deletar(Long id) {
        missoesRepository.deleteById(id);
    }

    // --- MÉTODOS AUXILIARES DE CONVERSÃO ---

    private MissoesDTO converterParaDTO(Missoes missao) {
        MissoesDTO dto = new MissoesDTO();
        dto.setIdMissao(missao.getIdMissao());
        dto.setNomeMissao(missao.getNomeMissao());
        dto.setDescricao(missao.getDescricao());
        dto.setGrauDificuldade(missao.getGrauDificuldade());
        return dto;
    }

    private Missoes converterParaEntidade(MissoesDTO dto) {
        Missoes missao = new Missoes();
        missao.setNomeMissao(dto.getNomeMissao());
        missao.setDescricao(dto.getDescricao());
        missao.setGrauDificuldade(dto.getGrauDificuldade());
        return missao;
    }
}