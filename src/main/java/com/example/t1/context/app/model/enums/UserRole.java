package com.example.t1.context.app.model.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum UserRole implements GrantedAuthority {

    ADMIN,
    VOTER;

    @Override
    public String getAuthority() {
        return super.toString().toUpperCase();
    }
}
