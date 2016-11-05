package com.pin2.pedrobino.support.auth;

import com.pin2.pedrobino.entities.person.Person;
import com.pin2.pedrobino.entities.user.User;
import com.pin2.pedrobino.entities.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // TODO find a real way to do this
        System.out.println(user.getPerson());

        return new AuthUser(user);
    }

}