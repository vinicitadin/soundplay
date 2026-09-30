package com.example.soundplay.service;

import com.example.soundplay.entity.Artista;
import com.example.soundplay.repository.ArtistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public List<Artista> listarTodos() {
        return artistaRepository.findAll();
    }

    public Artista salvar(Artista artista) {
        return artistaRepository.save(artista);
    }
}
