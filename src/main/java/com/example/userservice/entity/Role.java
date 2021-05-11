package com.example.userservice.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    PARTNER("ROLE_PARTNER"),
    CLIENT("ROLE_CLIENT");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
