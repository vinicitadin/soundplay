package com.example.soundplay.controller;

import com.example.soundplay.entity.Playlist;
import com.example.soundplay.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> listarTodas() {
        return ResponseEntity.ok(playlistService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Playlist> criar(@RequestBody Playlist playlist) {
        Playlist playlistCriada = playlistService.criar(playlist);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(playlistCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> atualizar(
            @PathVariable Long id,
            @RequestBody Playlist playlist
    ) {
        Playlist playlistAtualizada =
                playlistService.atualizar(id, playlist);

        return ResponseEntity.ok(playlistAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        playlistService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}