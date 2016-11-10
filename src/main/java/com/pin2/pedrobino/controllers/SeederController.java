package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.entities.administrator.Administrator;
import com.pin2.pedrobino.entities.administrator.AdministratorsRepository;
import com.pin2.pedrobino.entities.city.CitiesRepository;
import com.pin2.pedrobino.entities.city.City;
import com.pin2.pedrobino.entities.city.State;
import com.pin2.pedrobino.entities.city.StatesRepository;
import com.pin2.pedrobino.entities.driver.Driver;
import com.pin2.pedrobino.entities.driver.DriversRepository;
import com.pin2.pedrobino.entities.user.User;
import com.pin2.pedrobino.entities.user.UsersRepository;
import com.pin2.pedrobino.entities.client.Client;
import com.pin2.pedrobino.entities.client.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private DriversRepository driversRepository;

    @PostConstruct
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

        // States
        State sc = new State("SC", "Santa Catarina");
        State pr = new State("PR", "Paraná");
        State rs = new State("RS", "Rio Grande do Sul");
        statesRepository.save(sc);
        statesRepository.save(rs);
        statesRepository.save(pr);

        City fl = new City("Florianópolis", sc);
        citiesRepository.save(fl);

        City cb = new City("Curitiba", pr);
        citiesRepository.save(cb);

        City pa = new City("Porto Alegre", rs);
        citiesRepository.save(pa);

        // Drivers
        Driver driver = new Driver("João Machado", 5000, 20, 3000, "D", pa);
        driversRepository.save(driver);

        return "All seeded.";
    }

}
