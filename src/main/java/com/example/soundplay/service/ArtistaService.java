package com.example.soundplay.service;

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
public class ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    public ArtistaService(ArtistaRepository artistaRepository, AlbumRepository albumRepository) {
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
    }

    public List<Artista> listarTodos() {
        return artistaRepository.findAll();
    }

    public List<Artista> buscarPorNome(String nome) {
        return artistaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Artista salvar(Artista artista) {
        return artistaRepository.save(artista);
    }

    public Optional<Artista> atualizar(Long id, Artista artistaAtualizado) {
        return artistaRepository.findById(id)
                .map(artista -> {
                    artista.setNome(artistaAtualizado.getNome());
                    artista.setGenero(artistaAtualizado.getGenero());
                    artista.setBiografia(artistaAtualizado.getBiografia());
                    return artistaRepository.save(artista);
                });
    }

    @Transactional
    public boolean deletar(Long id) {
        if (!artistaRepository.existsById(id)) {
            return false;
        }

        if (albumRepository.existsByArtistaId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir um artista que possui álbuns"
            );
        }

        artistaRepository.deleteById(id);
        return true;
    }
}
