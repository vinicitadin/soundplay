package com.example.soundplay.controller;

import com.example.soundplay.doc.AlbumControllerDoc;
import com.example.soundplay.dto.request.AlbumRequest;
import com.example.soundplay.dto.response.AlbumResponse;
import com.example.soundplay.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albuns")
public class AlbumController implements AlbumControllerDoc {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponse>> listar() {
        return ResponseEntity.ok(albumService.listarTodos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<AlbumResponse>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(albumService.buscarPorTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<AlbumResponse> salvar(@Valid @RequestBody AlbumRequest album) {
        AlbumResponse albumSalvo = albumService.salvar(album);
        return ResponseEntity.status(HttpStatus.CREATED).body(albumSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponse> atualizar(@PathVariable Long id, @Valid @RequestBody AlbumRequest albumAtualizado) {
        return albumService.atualizar(id, albumAtualizado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = albumService.deletar(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
