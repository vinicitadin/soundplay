package com.example.soundplay.service;

import com.example.soundplay.entity.Playlist;
import com.example.soundplay.repository.PlaylistRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<Playlist> listarTodas() {
        return playlistRepository.findAll();
    }

    public Playlist buscarPorId(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Playlist não encontrada"
                ));
    }

    public Playlist criar(Playlist playlist) {
        validarPlaylist(playlist);

        playlist.setId(null);
        normalizarDados(playlist);

        return playlistRepository.save(playlist);
    }

    public Playlist atualizar(Long id, Playlist dadosAtualizados) {
        Playlist playlistExistente = buscarPorId(id);

        validarPlaylist(dadosAtualizados);
        normalizarDados(dadosAtualizados);

        playlistExistente.setNome(dadosAtualizados.getNome());
        playlistExistente.setDescricao(dadosAtualizados.getDescricao());
        playlistExistente.setPublica(dadosAtualizados.getPublica());

        return playlistRepository.save(playlistExistente);
    }

    public void excluir(Long id) {
        Playlist playlist = buscarPorId(id);

        playlistRepository.delete(playlist);
    }

    private void validarPlaylist(Playlist playlist) {
        if (playlist == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados da playlist são obrigatórios"
            );
        }

        if (playlist.getDescricao() == null ||
                playlist.getDescricao().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A descrição da playlist é obrigatória"
            );
        }

        if (playlist.getPublica() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O campo publica é obrigatório"
            );
        }

        if (playlist.getNome() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da playlist é obrigatório"
            );
        }

        if (playlist.getNome() != null &&
                playlist.getNome().trim().length() > 150) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da playlist deve possuir no máximo 150 caracteres"
            );
        }
    }

    private void normalizarDados(Playlist playlist) {
        if (playlist.getNome() != null) {
            String nome = playlist.getNome().trim();

            playlist.setNome(nome.isEmpty() ? null : nome);
        }

        playlist.setDescricao(playlist.getDescricao().trim());
    }
}