package com.example.soundplay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlbumRequest(
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
        String titulo,

        Integer anoLancamento,

        @Size(max = 500, message = "A capa deve ter no máximo 500 caracteres")
        String capa,

        @NotNull(message = "artistaId é obrigatório")
        Long artistaId) {
}
