package com.example.soundplay.repository;

import com.example.soundplay.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O username do backend é o e-mail
    Optional<UserDetails> findUserByEmail(String username);
}