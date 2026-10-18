package com.example.soundplay.dto.response;

import com.example.soundplay.entity.Playlist;

import java.util.List;

public record PlaylistResponse(
        Long id,
        String nome,
        String descricao,
        Boolean publica,
        Long usuarioId,
        List<MusicaResponse> musicas) {

    public static PlaylistResponse from(Playlist playlist) {
        return new PlaylistResponse(
                playlist.getId(),
                playlist.getNome(),
                playlist.getDescricao(),
                playlist.getPublica(),
                playlist.getUsuario().getId(),
                playlist.getMusicas().stream().map(MusicaResponse::from).toList()
        );
    }
}
