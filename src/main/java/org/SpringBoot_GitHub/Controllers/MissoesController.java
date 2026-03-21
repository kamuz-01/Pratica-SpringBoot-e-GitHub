package org.SpringBoot_GitHub.Controllers;

import org.SpringBoot_GitHub.Models.DTOs.MissoesDTO;
import org.SpringBoot_GitHub.Services.MissoesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<MissoesDTO> missao = missoesService.buscarPorId(id);
        return missao.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
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