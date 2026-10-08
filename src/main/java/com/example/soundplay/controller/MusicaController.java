package com.example.soundplay.controller;

import com.example.soundplay.entity.Musica;
import com.example.soundplay.repository.MusicaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.soundplay.doc.MusicaControllerDoc;

import java.util.List;

@RestController
@RequestMapping("/musicas")
public class MusicaController implements MusicaControllerDoc {

    private final MusicaRepository musicaRepository;

    public MusicaController(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Musica>> listar() {
        return ResponseEntity.ok(musicaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Musica> buscarPorId(@PathVariable Long id) {
        return musicaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Musica>> buscarPorTitulo(
            @RequestParam String titulo) {

        return ResponseEntity.ok(
                musicaRepository.findByTituloContainingIgnoreCase(titulo)
        );
    }

    @PostMapping
    public ResponseEntity<Musica> cadastrar(@RequestBody Musica musica) {

        Musica musicaSalva = musicaRepository.save(musica);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(musicaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Musica> atualizar(
            @PathVariable Long id,
            @RequestBody Musica musica) {

        return musicaRepository.findById(id)
                .map(musicaExistente -> {

                    musicaExistente.setTitulo(musica.getTitulo());
                    musicaExistente.setDuracao(musica.getDuracao());
                    musicaExistente.setUrl(musica.getUrl());

                    Musica musicaAtualizada =
                            musicaRepository.save(musicaExistente);

                    return ResponseEntity.ok(musicaAtualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        if (!musicaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        musicaRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
