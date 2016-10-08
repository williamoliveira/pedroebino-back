package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.administrator.Administrator;
import com.pin2.pedrobino.domain.administrator.AdministratorsRepository;
import com.pin2.pedrobino.domain.auth.User;
import com.pin2.pedrobino.domain.auth.UsersRepository;
import com.pin2.pedrobino.domain.client.Client;
import com.pin2.pedrobino.domain.client.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seed")
@Transactional
public class SeederController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AdministratorsRepository administratorsRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @RequestMapping
    public String seed() {

        // Administrator
        Administrator admin = new Administrator("Administrator");
        administratorsRepository.save(admin);

        User adminUser = new User("admin@admin.com", (new BCryptPasswordEncoder()).encode("12345678"), admin);
        usersRepository.save(adminUser);

        // Client
        Client client = new Client("John Doe");
        clientsRepository.save(client);

        User clientUser = new User("johndoe@gmail.com", (new BCryptPasswordEncoder()).encode("12345678"), client);
        usersRepository.save(clientUser);


        return "All seeded.";
    }

}
