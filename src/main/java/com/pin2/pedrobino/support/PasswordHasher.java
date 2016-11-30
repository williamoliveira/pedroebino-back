package com.pin2.pedrobino.support;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    public static String hash(String password) {
        return (new BCryptPasswordEncoder()).encode(password);
    }

    public static boolean mathes(String hash, String password) {
        return (new BCryptPasswordEncoder()).matches(password, hash);
    }
}
