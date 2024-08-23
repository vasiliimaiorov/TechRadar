package com.example.t1.context.app.model.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {

    ADMIN("ADMIN"),
    VOTER("VOTER");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }
}
