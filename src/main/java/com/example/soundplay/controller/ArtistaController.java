package com.example.soundplay.controller;

import com.example.soundplay.entity.Artista;
import com.example.soundplay.service.ArtistaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public List<Artista> listar() {
        return artistaService.listarTodos();
    }

    @PostMapping
    public Artista salvar(@RequestBody Artista artista) {
        return artistaService.salvar(artista);
    }
}
