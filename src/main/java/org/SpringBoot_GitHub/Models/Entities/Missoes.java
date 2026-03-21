package org.SpringBoot_GitHub.Models.Entities;

import org.SpringBoot_GitHub.Models.Enums.GrauDificuldade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "missoes")
public class Missoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_missao")
    private Long idMissao;

    @Column(name = "nome_missao", nullable = false)
    private String nomeMissao;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "grau_dificuldade", nullable = false)
    private GrauDificuldade grauDificuldade;

    // Relacionamento @OneToMany - Uma missão pode ser feita por vários usuários
    @OneToMany(mappedBy = "missao")
    @JsonIgnore
    private List<Usuario> usuarios;
}