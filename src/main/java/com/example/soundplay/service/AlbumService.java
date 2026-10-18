package com.example.soundplay.service;

import com.example.soundplay.dto.request.AlbumRequest;
import com.example.soundplay.dto.response.AlbumResponse;
import com.example.soundplay.entity.Album;
import com.example.soundplay.entity.Artista;
import com.example.soundplay.repository.AlbumRepository;
import com.example.soundplay.repository.ArtistaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;

    public AlbumService(AlbumRepository albumRepository, ArtistaRepository artistaRepository) {
        this.albumRepository = albumRepository;
        this.artistaRepository = artistaRepository;
    }

    @Transactional(readOnly = true)
    public List<AlbumResponse> listarTodos() {
        return albumRepository.findAll().stream().map(AlbumResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<AlbumResponse> buscarPorTitulo(String titulo) {
        return albumRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(AlbumResponse::from)
                .toList();
    }

    @Transactional
    public AlbumResponse salvar(AlbumRequest request) {
        Album album = new Album();
        preencher(album, request);
        return AlbumResponse.from(albumRepository.save(album));
    }

    @Transactional
    public Optional<AlbumResponse> atualizar(Long id, AlbumRequest request) {
        return albumRepository.findById(id)
                .map(album -> {
                    preencher(album, request);
                    return AlbumResponse.from(albumRepository.save(album));
                });
    }

    @Transactional
    public boolean deletar(Long id) {
        if (!albumRepository.existsById(id)) {
            return false;
        }
        albumRepository.deleteById(id);
        return true;
    }

    private void preencher(Album album, AlbumRequest request) {
        Artista artista = artistaRepository.findById(request.artistaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Artista não encontrado"
                ));

        album.setTitulo(request.titulo().trim());
        album.setAno_lancamento(request.anoLancamento());
        album.setCapa(request.capa());
        album.setArtista(artista);
    }
}
