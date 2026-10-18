package com.example.soundplay.service;

import com.example.soundplay.dto.request.MusicaRequest;
import com.example.soundplay.dto.response.MusicaResponse;
import com.example.soundplay.entity.Album;
import com.example.soundplay.entity.Artista;
import com.example.soundplay.entity.Musica;
import com.example.soundplay.repository.AlbumRepository;
import com.example.soundplay.repository.ArtistaRepository;
import com.example.soundplay.repository.MusicaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MusicaService {

    private final MusicaRepository musicaRepository;
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    public MusicaService(MusicaRepository musicaRepository,
                         ArtistaRepository artistaRepository,
                         AlbumRepository albumRepository) {
        this.musicaRepository = musicaRepository;
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
    }

    @Transactional(readOnly = true)
    public List<MusicaResponse> listar() {
        return musicaRepository.findAll().stream().map(MusicaResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Optional<MusicaResponse> buscarPorId(Long id) {
        return musicaRepository.findById(id).map(MusicaResponse::from);
    }

    @Transactional(readOnly = true)
    public List<MusicaResponse> buscarPorTitulo(String titulo) {
        return musicaRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(MusicaResponse::from)
                .toList();
    }

    @Transactional
    public MusicaResponse cadastrar(MusicaRequest request) {
        Musica musica = new Musica();
        preencher(musica, request);
        return MusicaResponse.from(musicaRepository.save(musica));
    }

    @Transactional
    public Optional<MusicaResponse> atualizar(Long id, MusicaRequest request) {
        return musicaRepository.findById(id)
                .map(musica -> {
                    preencher(musica, request);
                    return MusicaResponse.from(musicaRepository.save(musica));
                });
    }

    @Transactional
    public boolean excluir(Long id) {
        if (!musicaRepository.existsById(id)) {
            return false;
        }
        musicaRepository.deleteById(id);
        return true;
    }

    private void preencher(Musica musica, MusicaRequest request) {
        musica.setTitulo(request.titulo().trim());
        musica.setDuracao(request.duracao());
        musica.setUrl(request.url());

        Album album = null;
        if (request.albumId() != null) {
            album = albumRepository.findById(request.albumId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Álbum não encontrado"
                    ));
        }

        Artista artista = null;
        if (request.artistaId() != null) {
            artista = artistaRepository.findById(request.artistaId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Artista não encontrado"
                    ));
        }

        if (album != null) {
            Artista artistaDoAlbum = album.getArtista();

            if (artista == null) {
                artista = artistaDoAlbum;
            } else if (!artista.getId().equals(artistaDoAlbum.getId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O álbum informado não pertence ao artista informado"
                );
            }
        }

        musica.setAlbum(album);
        musica.setArtista(artista);
    }
}
