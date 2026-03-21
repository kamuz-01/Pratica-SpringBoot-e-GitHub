package org.SpringBoot_GitHub.Models.DTOs;

import org.SpringBoot_GitHub.Models.Enums.GrauDificuldade;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissoesDTO {

    @Column(name = "id_missao")
    private Long idMissao;
    private String nomeMissao;
    private String descricao;
    private GrauDificuldade grauDificuldade;
}