package org.SpringBoot_GitHub.Services;

import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.UsuarioDTO;
import org.SpringBoot_GitHub.Models.Entities.Missoes;
import org.SpringBoot_GitHub.Models.Entities.Usuario;
import org.SpringBoot_GitHub.Repositories.MissoesRepository;
import org.SpringBoot_GitHub.Repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MissoesRepository missoesRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, MissoesRepository missoesRepository) {
        this.usuarioRepository = usuarioRepository;
        this.missoesRepository = missoesRepository;
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(this::converterParaDTO);
    }

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = converterParaEntidade(usuarioDTO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return converterParaDTO(usuarioSalvo);
    }

    // --- Método de atualização ---
    public UsuarioDTO atualizar(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursosNaoEncontradosException("Usuário não encontrado com o ID: " + id));

        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setSobrenome(usuarioDTO.getSobrenome());
        usuarioExistente.setCpf(usuarioDTO.getCpf());
        usuarioExistente.setDataNascimento(usuarioDTO.getDataNascimento());
        usuarioExistente.setTelefone(usuarioDTO.getTelefone());
        usuarioExistente.setEmail(usuarioDTO.getEmail());

        if (usuarioDTO.getIdMissao() != null) {
            Missoes missao = missoesRepository.findById(usuarioDTO.getIdMissao())
                    .orElseThrow(() -> new RecursosNaoEncontradosException("Missão não encontrada com o ID: " + usuarioDTO.getIdMissao()));
            usuarioExistente.setMissao(missao);
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return converterParaDTO(usuarioAtualizado);
    }

    // --- Verifica se o ID existe antes de deletar ---
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursosNaoEncontradosException("Usuário não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // --- MÉTODOS AUXILIARES DE CONVERSÃO ---

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setSobrenome(usuario.getSobrenome());
        dto.setCpf(usuario.getCpf());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setTelefone(usuario.getTelefone());
        dto.setEmail(usuario.getEmail());
        
        if (usuario.getMissao() != null) {
            dto.setIdMissao(usuario.getMissao().getIdMissao());
        }
        return dto;
    }

    private Usuario converterParaEntidade(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setCpf(dto.getCpf());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEmail(dto.getEmail());

        if (dto.getIdMissao() != null) {
            Missoes missao = missoesRepository.findById(dto.getIdMissao())
                    .orElseThrow(() -> new RecursosNaoEncontradosException("Missão não encontrada com o ID: " + dto.getIdMissao()));
            usuario.setMissao(missao);
        }
        return usuario;
    }
}