package com.example.soundplay.service;

import com.example.soundplay.entity.Musica;
import com.example.soundplay.repository.MusicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicaService {

    private final MusicaRepository musicaRepository;

    public MusicaService(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    public List<Musica> listar() {
        return musicaRepository.findAll();
    }

    public Optional<Musica> buscarPorId(Long id) {
        return musicaRepository.findById(id);
    }

    public List<Musica> buscarPorTitulo(String titulo) {
        return musicaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Musica cadastrar(Musica musica) {
        return musicaRepository.save(musica);
    }

    public Optional<Musica> atualizar(Long id, Musica musica) {

        return musicaRepository.findById(id)
                .map(musicaExistente -> {

                    musicaExistente.setTitulo(musica.getTitulo());
                    musicaExistente.setDuracao(musica.getDuracao());
                    musicaExistente.setUrl(musica.getUrl());

                    return musicaRepository.save(musicaExistente);
                });
    }

    public boolean excluir(Long id) {

        if (!musicaRepository.existsById(id)) {
            return false;
        }

        musicaRepository.deleteById(id);
        return true;
    }
}