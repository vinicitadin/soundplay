ALTER TABLE playlist
    ADD COLUMN usuario_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_playlist_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE;

CREATE INDEX idx_playlist_usuario ON playlist (usuario_id);

CREATE TABLE playlist_musica (
    playlist_id BIGINT NOT NULL,
    musica_id   BIGINT NOT NULL,
    PRIMARY KEY (playlist_id, musica_id),
    CONSTRAINT fk_pm_playlist FOREIGN KEY (playlist_id) REFERENCES playlist (id) ON DELETE CASCADE,
    CONSTRAINT fk_pm_musica   FOREIGN KEY (musica_id)   REFERENCES musica (id)   ON DELETE CASCADE
);

CREATE INDEX idx_pm_musica ON playlist_musica (musica_id);

ALTER TABLE album
    ADD COLUMN artista_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_album_artista
    FOREIGN KEY (artista_id) REFERENCES artista (id);

CREATE INDEX idx_album_artista ON album (artista_id);

ALTER TABLE musica
    ADD COLUMN artista_id BIGINT,
    ADD COLUMN album_id   BIGINT,
    ADD CONSTRAINT fk_musica_artista FOREIGN KEY (artista_id) REFERENCES artista (id) ON DELETE SET NULL,
    ADD CONSTRAINT fk_musica_album   FOREIGN KEY (album_id)   REFERENCES album (id)   ON DELETE SET NULL;

CREATE INDEX idx_musica_artista ON musica (artista_id);
CREATE INDEX idx_musica_album   ON musica (album_id);