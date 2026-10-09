package com.example.soundplay.controller;

import com.example.soundplay.entity.Album;
import com.example.soundplay.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albuns")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<Album>> listar() {
        return ResponseEntity.ok(albumService.listarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Album>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(albumService.buscarPorTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<Album> salvar(@RequestBody Album album) {
        Album albumSalvo = albumService.salvar(album);
        return ResponseEntity.status(HttpStatus.CREATED).body(albumSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> atualizar(@PathVariable Long id, @RequestBody Album albumAtualizado) {
        return albumService.atualizar(id, albumAtualizado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = albumService.deletar(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
