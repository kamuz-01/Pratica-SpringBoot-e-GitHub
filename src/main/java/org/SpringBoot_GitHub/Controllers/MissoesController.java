package org.SpringBoot_GitHub.Controllers;

import org.SpringBoot_GitHub.GerenciamentoErros.RecursosNaoEncontradosException;
import org.SpringBoot_GitHub.Models.DTOs.MissoesDTO;
import org.SpringBoot_GitHub.Services.MissoesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@RequestMapping("/api/v1/missoes")
public class MissoesController {

    private final MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    @GetMapping
    public ResponseEntity<List<MissoesDTO>> listarTodas() {
        return ResponseEntity.ok(missoesService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissoesDTO> buscarPorId(@PathVariable Long id) {
        MissoesDTO missao = missoesService.buscarPorId(id)
                .orElseThrow(() -> new RecursosNaoEncontradosException("Missão não encontrada com o ID: " + id));
        return ResponseEntity.ok(missao);
    }

    @PostMapping
    public ResponseEntity<MissoesDTO> criar(@RequestBody MissoesDTO missaoDTO) {
        MissoesDTO novaMissao = missoesService.salvar(missaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMissao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        missoesService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}