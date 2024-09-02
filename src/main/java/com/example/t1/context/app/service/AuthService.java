package com.example.t1.context.app.service;

import com.example.t1.api.security.jwt.JwtProvider;
import com.example.t1.context.app.api.JwtResponse;
import com.example.t1.context.app.api.RegisterRequest;
import com.example.t1.context.app.model.User;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final PasswordEncoder encoder;

    public JwtResponse login(@NonNull String login, @NonNull String password) throws AuthException {
        try {
            final var user = userService.getByLogin(login);

            if (encoder.matches(password, user.getPassword())) {
                final var accessToken = jwtProvider.generateAccessToken(user);
                final var refreshToken = jwtProvider.generateRefreshToken(user);
                return new JwtResponse(accessToken, refreshToken, user.getRole());
            }
            throw new AuthException("Wrong password");
        } catch (UsernameNotFoundException e) {
            throw new AuthException("User not found");
        }
    }

    public User register(@NonNull RegisterRequest request) {
        return userService.addUser(request);
    }

    public JwtResponse getNewAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final var username = jwtProvider.getRefreshClaims(refreshToken).getSubject();
            final var dbUser = userService.getByLogin(username);
            final var newAccessToken = jwtProvider.generateAccessToken(dbUser);
            return new JwtResponse(newAccessToken, null, dbUser.getRole());
        }
        throw new AuthException("Invalid refresh JWT token");
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final var username = jwtProvider.getRefreshClaims(refreshToken).getSubject();
            final var dbUser = userService.getByLogin(username);
            final var newAccessToken = jwtProvider.generateAccessToken(dbUser);
            final var newRefreshToken = jwtProvider.generateRefreshToken(dbUser);
            return new JwtResponse(newAccessToken, newRefreshToken, dbUser.getRole());
        }
        throw new AuthException("Invalid refresh JWT token");
    }

    public boolean checkIfUserExists(String login) {
        return userService.checkIfExists(login);
    }

}