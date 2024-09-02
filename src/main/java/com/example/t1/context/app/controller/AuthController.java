package com.example.t1.context.app.controller;

import com.example.t1.context.app.api.LoginRequest;
import com.example.t1.context.app.api.JwtRefreshRequest;
import com.example.t1.context.app.api.RegisterRequest;
import com.example.t1.context.app.service.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            var jwtResponse = authService.login(request.login(), request.password());
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        if (authService.checkIfUserExists(request.login())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exist");
        }

        final var newUser = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser.getRole().toString());
    }

    @PostMapping("/token")
    public ResponseEntity<?> getNewAccessToken(@RequestBody @Valid JwtRefreshRequest request) {
        try {
            var jwtResponse = authService.getNewAccessToken(request.refreshToken());
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> getNewRefreshToken(@RequestBody @Valid JwtRefreshRequest request) {
        try {
            var jwtResponse = authService.refresh(request.refreshToken());
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}