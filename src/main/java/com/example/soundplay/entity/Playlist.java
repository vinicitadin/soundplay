package com.example.soundplay.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = true, length = 150)
    private String nome;

    @Column (nullable = false)
    private String descricao;

    @Column (nullable = false)
    private Boolean publica;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "playlist_musica",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    private Set<Musica> musicas = new HashSet<>();

    public void adicionarMusica(Musica musica) {
        musicas.add(musica);
        musica.getPlaylists().add(this);
    }

    public void removerMusica(Musica musica) {
        musicas.remove(musica);
        musica.getPlaylists().remove(this);
    }

    public Playlist () {

    }

    public Playlist (String nome, String descricao, Boolean publica) {
        this.nome = nome;
        this.descricao = descricao;
        this.publica = publica;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getPublica() {
        return publica;
    }

    public void setPublica(Boolean publica) {
        this.publica = publica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Musica> getMusicas() {
        return musicas;
    }
}
