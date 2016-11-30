package com.pin2.pedrobino.support.auth;

import com.pin2.pedrobino.domain.user.User;
import com.pin2.pedrobino.domain.user.UsersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }

        // Trigger lazy load
        Hibernate.initialize(user.getPerson());

        return new AuthUser(user);
    }

}