package com.example.soundplay.repository;

import com.example.soundplay.entity.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    List<Musica> findByTituloContainingIgnoreCase(String titulo);

}