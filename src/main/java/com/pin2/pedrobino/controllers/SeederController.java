package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.administrator.Administrator;
import com.pin2.pedrobino.domain.administrator.AdministratorsRepository;
import com.pin2.pedrobino.domain.city.CitiesRepository;
import com.pin2.pedrobino.domain.city.City;
import com.pin2.pedrobino.domain.city.State;
import com.pin2.pedrobino.domain.city.StatesRepository;
import com.pin2.pedrobino.domain.client.Client;
import com.pin2.pedrobino.domain.client.ClientsRepository;
import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.driver.DriversRepository;
import com.pin2.pedrobino.domain.request.*;
import com.pin2.pedrobino.domain.settings.Settings;
import com.pin2.pedrobino.domain.settings.SettingsService;
import com.pin2.pedrobino.domain.truck.Truck;
import com.pin2.pedrobino.domain.truck.TrucksRepository;
import com.pin2.pedrobino.domain.user.User;
import com.pin2.pedrobino.domain.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
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

    @Autowired
    private TrucksRepository trucksRepository;

    @Autowired
    private ProposalsRepository proposalsRepository;

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private ProposalsGenerator proposalsGenerator;

    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private SettingsService settingsService;

    @PostConstruct
    public String seed() {

        settingsService.saveSettings(new Settings(10));

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

        City florianopolis = new City("Florianópolis", sc);
        citiesRepository.save(florianopolis);

        City curitiba = new City("Curitiba", pr);
        citiesRepository.save(curitiba);

        City portoAlegre = new City("Porto Alegre", rs);
        citiesRepository.save(portoAlegre);

        // Drivers
        Driver driver = new Driver("João Machado", 25 * 365, 30, 3000, "D", portoAlegre);
        driversRepository.save(driver);
        Driver driver2 = new Driver("John Doe", 10 * 365, 20, 5000, "E", curitiba);
        driversRepository.save(driver2);

        // Trucks
        Truck truck = new Truck("Volvo 454", "D", 2000, 5);
        trucksRepository.save(truck);

        Truck truck2 = new Truck("Volvo 234", "D", 1000, 4);
        trucksRepository.save(truck2);

        Truck truck3 = new Truck("Volvo 324", "D", 890, 3);
        trucksRepository.save(truck3);

        // Requests
        Request request = requestFactory.create(new Request(
                true,
                createDate("17/12/2016 00:00"),
                500,
                portoAlegre,
                florianopolis,
                client
        ));

        requestsRepository.save(request);

        return "All seeded.";
    }

    @EventListener(ContextRefreshedEvent.class)
    void contextRefreshedEvent() {
        System.out.println("Context Refreshed");

//        System.out.println(
//                trucksRepository.findAvailableTrucks(
//                        createDate("15/12/2016 08:00"),
//                        createDate("16/12/2016 07:00"),
//                        500.0
//                )
//        );

//        System.out.println(
//                driversRepository.findAvailableDrivers(
//                        createDate("15/12/2016 08:00"),
//                        createDate("16/12/2016 07:00"),
//                        citiesRepository.findByName("Curitiba"),
//                        400
//                )
//        );
//
//        Request request = requestsRepository.findAll().get(0);
//
//        List<Proposal> proposals = proposalsGenerator.generateProposals(request);
//
//        System.out.println(proposals);

    }


    private LocalDateTime createDate(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

}
