package com.example.t1.context.app.api;

import com.example.t1.context.app.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

public record JwtResponse (@NotBlank String accessToken,
                           @NotBlank String refreshToken,
                           UserRole role) {}
