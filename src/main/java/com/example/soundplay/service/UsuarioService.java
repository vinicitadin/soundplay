package com.example.soundplay.service;

import com.example.soundplay.entity.Usuario;
import com.example.soundplay.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario cadastrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> atualizar(Long id, Usuario usuario) {

        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {

                    usuarioExistente.setNome(usuario.getNome());
                    usuarioExistente.setEmail(usuario.getEmail());
                    usuarioExistente.setSenha(usuario.getSenha());

                    return usuarioRepository.save(usuarioExistente);
                });
    }

    public boolean excluir(Long id) {

        if (!usuarioRepository.existsById(id)) {
            return false;
        }

        usuarioRepository.deleteById(id);
        return true;
    }
}