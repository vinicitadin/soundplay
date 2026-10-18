package com.example.soundplay.repository;

import com.example.soundplay.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUsuarioId(Long usuarioId);

    @Query("select p from Playlist p where p.publica = true or p.usuario.id = :usuarioId")
    List<Playlist> findVisiveis(@Param("usuarioId") Long usuarioId);
}
