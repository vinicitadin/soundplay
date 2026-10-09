package com.example.soundplay.service;

import com.example.soundplay.entity.Album;
import com.example.soundplay.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> listarTodos() {
        return albumRepository.findAll();
    }

    public List<Album> buscarPorTitulo(String titulo) {
        return albumRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Album salvar(Album album) {
        return albumRepository.save(album);
    }

    public Optional<Album> atualizar(Long id, Album albumAtualizado) {
        return albumRepository.findById(id)
                .map(album -> {
                    album.setTitulo(albumAtualizado.getTitulo());
                    album.setAno_lancamento(albumAtualizado.getAno_lancamento());
                    album.setCapa(albumAtualizado.getCapa());
                    return albumRepository.save(album);
                });
    }

    public boolean deletar(Long id) {
        if (!albumRepository.existsById(id)) {
            return false;
        }
        albumRepository.deleteById(id);
        return true;
    }
}
