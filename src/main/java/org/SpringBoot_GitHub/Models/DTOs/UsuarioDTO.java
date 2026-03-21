package org.SpringBoot_GitHub.Models.DTOs;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Long idUsuario;
    private String nome;
    private String sobrenome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;

    @Column(name = "id_missao", nullable = false)
    private Long idMissao;
}