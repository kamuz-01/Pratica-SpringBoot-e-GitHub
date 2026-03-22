package org.SpringBoot_GitHub.Models.DTOs;

import org.SpringBoot_GitHub.Models.Enums.GrauDificuldade;
import org.SpringBoot_GitHub.Models.Enums.StatusMissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MissoesDTO {

    private Long idMissao;

    @NotBlank(message = "O nome da missão é obrigatório")
    private String nomeMissao;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O grau de dificuldade é obrigatório")
    private GrauDificuldade grauDificuldade;

    @NotNull(message = "O status da missão é obrigatório")
    private StatusMissao statusMissao;
}