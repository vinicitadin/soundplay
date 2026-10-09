package com.example.soundplay.controller;

import com.example.soundplay.doc.ArtistaControllerDoc;
import com.example.soundplay.entity.Artista;
import com.example.soundplay.service.ArtistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artistas")
public class ArtistaController implements ArtistaControllerDoc {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public ResponseEntity<List<Artista>> listar() {
        return ResponseEntity.ok(artistaService.listarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Artista>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(artistaService.buscarPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<Artista> salvar(@RequestBody Artista artista) {
        Artista artistaSalvo = artistaService.salvar(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(artistaSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @RequestBody Artista artistaAtualizado) {
        return artistaService.atualizar(id, artistaAtualizado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = artistaService.deletar(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}