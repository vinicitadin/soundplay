package com.example.soundplay.controller;

import com.example.soundplay.entity.Album;
import com.example.soundplay.service.AlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albuns")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public List<Album> listar() {
        return albumService.listarTodos();
    }

    @PostMapping
    public Album salvar(@RequestBody Album album) {
        return albumService.salvar(album);
    }
}
