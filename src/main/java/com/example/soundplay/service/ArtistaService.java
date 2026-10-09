package com.example.soundplay.service;

import com.example.soundplay.entity.Artista;
import com.example.soundplay.repository.ArtistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
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

    public boolean deletar(Long id) {
        if (!artistaRepository.existsById(id)) {
            return false;
        }
        artistaRepository.deleteById(id);
        return true;
    }
}