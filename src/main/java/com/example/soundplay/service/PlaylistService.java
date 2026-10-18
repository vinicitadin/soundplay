package com.example.soundplay.service;

import com.example.soundplay.config.JWTUserData;
import com.example.soundplay.dto.request.PlaylistRequest;
import com.example.soundplay.dto.response.PlaylistResponse;
import com.example.soundplay.entity.Musica;
import com.example.soundplay.entity.Playlist;
import com.example.soundplay.entity.Usuario;
import com.example.soundplay.repository.MusicaRepository;
import com.example.soundplay.repository.PlaylistRepository;
import com.example.soundplay.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UsuarioRepository usuarioRepository;
    private final MusicaRepository musicaRepository;

    public PlaylistService(PlaylistRepository playlistRepository,
                           UsuarioRepository usuarioRepository,
                           MusicaRepository musicaRepository) {
        this.playlistRepository = playlistRepository;
        this.usuarioRepository = usuarioRepository;
        this.musicaRepository = musicaRepository;
    }

    @Transactional(readOnly = true)
    public List<PlaylistResponse> listarTodas() {
        return playlistRepository.findVisiveis(usuarioLogadoId()).stream()
                .map(PlaylistResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PlaylistResponse> listarMinhas() {
        return playlistRepository.findByUsuarioId(usuarioLogadoId()).stream()
                .map(PlaylistResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlaylistResponse buscarPorId(Long id) {
        Playlist playlist = buscarEntidade(id);

        if (!Boolean.TRUE.equals(playlist.getPublica())) {
            garantirDono(playlist);
        }

        return PlaylistResponse.from(playlist);
    }

    @Transactional
    public PlaylistResponse criar(PlaylistRequest request) {
        validar(request);

        Usuario dono = usuarioRepository.findById(usuarioLogadoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuário autenticado não encontrado"
                ));

        Playlist playlist = new Playlist(
                request.nome().trim(),
                request.descricao().trim(),
                request.publica()
        );
        playlist.setUsuario(dono);

        return PlaylistResponse.from(playlistRepository.save(playlist));
    }

    @Transactional
    public PlaylistResponse atualizar(Long id, PlaylistRequest request) {
        Playlist playlist = buscarEntidade(id);
        garantirDono(playlist);
        validar(request);

        playlist.setNome(request.nome().trim());
        playlist.setDescricao(request.descricao().trim());
        playlist.setPublica(request.publica());

        return PlaylistResponse.from(playlistRepository.save(playlist));
    }

    @Transactional
    public void excluir(Long id) {
        Playlist playlist = buscarEntidade(id);
        garantirDono(playlist);

        playlistRepository.delete(playlist);
    }

    @Transactional
    public PlaylistResponse adicionarMusica(Long playlistId, Long musicaId) {
        Playlist playlist = buscarEntidade(playlistId);
        garantirDono(playlist);

        Musica musica = buscarMusica(musicaId);

        if (playlist.getMusicas().contains(musica)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A música já está na playlist"
            );
        }

        playlist.adicionarMusica(musica);

        return PlaylistResponse.from(playlistRepository.save(playlist));
    }

    @Transactional
    public void removerMusica(Long playlistId, Long musicaId) {
        Playlist playlist = buscarEntidade(playlistId);
        garantirDono(playlist);

        Musica musica = buscarMusica(musicaId);

        if (!playlist.getMusicas().contains(musica)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "A música não está na playlist"
            );
        }

        playlist.removerMusica(musica);
        playlistRepository.save(playlist);
    }

    private Playlist buscarEntidade(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Playlist não encontrada"
                ));
    }

    private Musica buscarMusica(Long id) {
        return musicaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Música não encontrada"
                ));
    }

    private void garantirDono(Playlist playlist) {
        if (!playlist.getUsuario().getId().equals(usuarioLogadoId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para acessar esta playlist"
            );
        }
    }

    private Long usuarioLogadoId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof JWTUserData dados)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado"
            );
        }

        return dados.userId();
    }

    private void validar(PlaylistRequest request) {
        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Os dados da playlist são obrigatórios"
            );
        }

        if (request.nome() == null || request.nome().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da playlist é obrigatório"
            );
        }

        if (request.nome().trim().length() > 150) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da playlist deve possuir no máximo 150 caracteres"
            );
        }

        if (request.descricao() == null || request.descricao().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A descrição da playlist é obrigatória"
            );
        }

        if (request.publica() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O campo publica é obrigatório"
            );
        }
    }
}
