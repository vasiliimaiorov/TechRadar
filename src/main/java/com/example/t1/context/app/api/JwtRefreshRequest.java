package com.example.t1.context.app.api;

import jakarta.validation.constraints.NotBlank;

public record JwtRefreshRequest(@NotBlank String refreshToken) {}
