package org.SpringBoot_GitHub.Repositories;

import org.SpringBoot_GitHub.Models.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}