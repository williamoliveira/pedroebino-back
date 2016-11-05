package com.pin2.pedrobino.support.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pin2.pedrobino.entities.administrator.Administrator;
import com.pin2.pedrobino.entities.user.User;
import com.pin2.pedrobino.entities.client.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class AuthUser extends User implements UserDetails {
    private static final long serialVersionUID = 1L;

    public AuthUser(User user) {
        super(user);
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(AuthRole::new).collect(Collectors.toSet());
    }

    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();

        roles.add("USER");

        if(getPerson() instanceof Administrator){
            roles.add("ADMIN");
        }
        else if(getPerson() instanceof Client){
            roles.add("CLIENT");
        }

        return roles;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
