package org.SpringBoot_GitHub.Repositories;

import org.SpringBoot_GitHub.Models.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/* O repositório pede dois parâmetros: a entidade que representa o 
 * domínio e o tipo do ID do domínio */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}