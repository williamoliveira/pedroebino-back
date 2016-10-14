package com.pin2.pedrobino.support.auth;

import org.springframework.security.core.GrantedAuthority;

public class AuthRole implements GrantedAuthority {

    private String name;

    public AuthRole(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
