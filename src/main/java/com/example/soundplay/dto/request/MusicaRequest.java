package com.example.soundplay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MusicaRequest(
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
        String titulo,

        @NotNull(message = "Duração é obrigatória")
        @Positive(message = "A duração deve ser maior que zero")
        Integer duracao,

        @Size(max = 500, message = "A url deve ter no máximo 500 caracteres")
        String url,

        // opcionais: a música pode não ter artista nem álbum
        Long artistaId,
        Long albumId) {
}
