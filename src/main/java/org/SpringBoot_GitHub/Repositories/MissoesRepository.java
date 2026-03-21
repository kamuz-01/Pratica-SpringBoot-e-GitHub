package org.SpringBoot_GitHub.Repositories;

import org.SpringBoot_GitHub.Models.Entities.Missoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissoesRepository extends JpaRepository<Missoes, Long> {}