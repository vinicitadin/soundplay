package com.example.soundplay.dto.response;

import com.example.soundplay.entity.Album;

public record AlbumResponse(
        Long id,
        String titulo,
        Integer anoLancamento,
        String capa,
        Long artistaId,
        String artistaNome) {

    public static AlbumResponse from(Album album) {
        return new AlbumResponse(
                album.getId(),
                album.getTitulo(),
                album.getAno_lancamento(),
                album.getCapa(),
                album.getArtista().getId(),
                album.getArtista().getNome()
        );
    }
}
