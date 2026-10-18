package com.example.soundplay.controller;

import com.example.soundplay.doc.PlaylistControllerDoc;
import com.example.soundplay.dto.request.PlaylistRequest;
import com.example.soundplay.dto.response.PlaylistResponse;
import com.example.soundplay.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController implements PlaylistControllerDoc {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponse>> listarTodas() {
        return ResponseEntity.ok(playlistService.listarTodas());
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<PlaylistResponse>> listarMinhas() {
        return ResponseEntity.ok(playlistService.listarMinhas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlaylistResponse> criar(@RequestBody PlaylistRequest playlist) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playlistService.criar(playlist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponse> atualizar(
            @PathVariable Long id,
            @RequestBody PlaylistRequest playlist
    ) {
        return ResponseEntity.ok(playlistService.atualizar(id, playlist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        playlistService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/musicas/{musicaId}")
    public ResponseEntity<PlaylistResponse> adicionarMusica(
            @PathVariable Long id,
            @PathVariable Long musicaId
    ) {
        return ResponseEntity.ok(playlistService.adicionarMusica(id, musicaId));
    }

    @DeleteMapping("/{id}/musicas/{musicaId}")
    public ResponseEntity<Void> removerMusica(
            @PathVariable Long id,
            @PathVariable Long musicaId
    ) {
        playlistService.removerMusica(id, musicaId);

        return ResponseEntity.noContent().build();
    }
}
