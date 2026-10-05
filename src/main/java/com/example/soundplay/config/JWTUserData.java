package com.example.soundplay.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {
}