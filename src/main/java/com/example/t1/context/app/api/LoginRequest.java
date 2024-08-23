package com.example.t1.context.app.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank (message = "Login can't be blank")
        @Size(min = 5, max = 50)
        @Pattern (regexp = "^[a-zA-Z0-9_]*$", message = "Login can contain [a-zA-Z0-9_]")
        String login,

        @NotBlank (message = "Password can't be blank")
        @Size(min = 5, max = 50)
        @Pattern (regexp = "^[a-zA-Z0-9_]*$", message = "Password can contain [a-zA-Z0-9_]")
        String password) {}