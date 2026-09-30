package com.example.soundplay.service;

import com.example.soundplay.entity.Album;
import com.example.soundplay.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> listarTodos() {
        return albumRepository.findAll();
    }

    public Album salvar(Album album) {
        return albumRepository.save(album);
    }
}
