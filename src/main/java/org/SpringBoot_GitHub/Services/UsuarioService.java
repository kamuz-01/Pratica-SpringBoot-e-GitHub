package org.SpringBoot_GitHub.Services;

import org.SpringBoot_GitHub.Models.Entities.Usuario;
import org.SpringBoot_GitHub.Repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Injeção de dependência via construtor (recomendado)
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para listar todos os usuarios
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Método para buscar um usuario por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para salvar um novo usuario
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para deletar um usuario
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}