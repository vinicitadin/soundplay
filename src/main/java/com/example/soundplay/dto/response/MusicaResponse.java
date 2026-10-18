package com.example.soundplay.dto.response;

import com.example.soundplay.entity.Musica;

public record MusicaResponse(
        Long id,
        String titulo,
        Integer duracao,
        String url,
        Long artistaId,
        String artistaNome,
        Long albumId,
        String albumTitulo) {

    public static MusicaResponse from(Musica musica) {
        return new MusicaResponse(
                musica.getId(),
                musica.getTitulo(),
                musica.getDuracao(),
                musica.getUrl(),
                musica.getArtista() != null ? musica.getArtista().getId() : null,
                musica.getArtista() != null ? musica.getArtista().getNome() : null,
                musica.getAlbum() != null ? musica.getAlbum().getId() : null,
                musica.getAlbum() != null ? musica.getAlbum().getTitulo() : null
        );
    }
}
