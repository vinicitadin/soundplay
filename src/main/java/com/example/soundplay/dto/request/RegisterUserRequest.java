package com.example.soundplay.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(@NotEmpty(message = "Nome obrigatório")
                                  String nome,

                                  @NotEmpty(message = "E-mail obrigatório")
                                  String email,

                                  @NotEmpty(message = "Senha obrigatória")
                                  String senha) {
}
