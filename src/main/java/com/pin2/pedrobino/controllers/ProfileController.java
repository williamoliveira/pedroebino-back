package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.user.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping("/me")
    public User me(OAuth2Authentication auth) {
        return (User) auth.getPrincipal();
    }
}
