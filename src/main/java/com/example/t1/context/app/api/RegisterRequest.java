package com.example.t1.context.app.api;

import com.example.t1.context.app.model.enums.UserRole;
import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank (message = "Login can't be blank")
        @Size(min = 4, max = 50)
        @Pattern (regexp = "^[a-zA-Z0-9_]*$", message = "Login can contain [a-zA-Z0-9_]")
        String login,

        @NotBlank (message = "Password can't be blank")
        @Size(min = 3, max = 50)
        @Pattern (regexp = "^[a-zA-Z0-9_]*$", message = "Password can contain [a-zA-Z0-9_]")
        String password,

        @NotNull (message = "Role can't be null")
        UserRole role) {}
