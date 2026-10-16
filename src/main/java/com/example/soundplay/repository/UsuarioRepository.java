package com.example.soundplay.repository;

import com.example.soundplay.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O username do backend é o e-mail
    Optional<Usuario> findByEmail(String email);
}
