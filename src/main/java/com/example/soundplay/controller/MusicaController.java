package com.example.soundplay.controller;

import com.example.soundplay.doc.MusicaControllerDoc;
import com.example.soundplay.dto.request.MusicaRequest;
import com.example.soundplay.dto.response.MusicaResponse;
import com.example.soundplay.service.MusicaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musicas")
public class MusicaController implements MusicaControllerDoc {

    private final MusicaService musicaService;

    public MusicaController(MusicaService musicaService) {
        this.musicaService = musicaService;
    }

    @GetMapping
    public ResponseEntity<List<MusicaResponse>> listar() {
        return ResponseEntity.ok(musicaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaResponse> buscarPorId(@PathVariable Long id) {
        return musicaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<MusicaResponse>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(musicaService.buscarPorTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<MusicaResponse> cadastrar(@Valid @RequestBody MusicaRequest musica) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(musicaService.cadastrar(musica));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MusicaRequest musica) {

        return musicaService.atualizar(id, musica)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!musicaService.excluir(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
