package com.example.soundplay.repository;

import com.example.soundplay.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}
