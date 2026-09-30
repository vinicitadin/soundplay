package com.example.soundplay.entity;

import jakarta.persistence.*;

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

}
