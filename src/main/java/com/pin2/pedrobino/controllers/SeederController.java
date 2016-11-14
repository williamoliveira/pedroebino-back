package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.entities.administrator.Administrator;
import com.pin2.pedrobino.entities.administrator.AdministratorsRepository;
import com.pin2.pedrobino.entities.city.CitiesRepository;
import com.pin2.pedrobino.entities.city.City;
import com.pin2.pedrobino.entities.city.State;
import com.pin2.pedrobino.entities.city.StatesRepository;
import com.pin2.pedrobino.entities.driver.Driver;
import com.pin2.pedrobino.entities.driver.DriversRepository;
import com.pin2.pedrobino.entities.request.Proposal;
import com.pin2.pedrobino.entities.request.ProposalsRepository;
import com.pin2.pedrobino.entities.request.Request;
import com.pin2.pedrobino.entities.request.RequestsRepository;
import com.pin2.pedrobino.entities.truck.Truck;
import com.pin2.pedrobino.entities.truck.TrucksRepository;
import com.pin2.pedrobino.entities.user.User;
import com.pin2.pedrobino.entities.user.UsersRepository;
import com.pin2.pedrobino.entities.client.Client;
import com.pin2.pedrobino.entities.client.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @PostConstruct
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

        // Trucks
        Truck truck = new Truck("Volvo 454", "D", 2000, 5);
        trucksRepository.save(truck);

        Truck truck2 = new Truck("Volvo 234", "D", 1000, 4);
        trucksRepository.save(truck2);

        Truck truck3 = new Truck("Volvo 324", "D", 890, 3);
        trucksRepository.save(truck3);

        // Requests
        Request request = new Request(
                true,
                createDate("17/12/2016 00:00"),
                8*60*60,
                500,
                500,
                "pending",
                fl,
                pa,
                client
        );
        requestsRepository.save(request);

        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver);

        // Proposals
        Proposal proposal1 = new Proposal(
                createDate("16/12/2016 08:00"),
                createDate("16/12/2016 20:00"),
                truck,
                drivers,
                request
        );
        proposalsRepository.save(proposal1);

//        testTrucks();

        return "All seeded.";
    }

    @EventListener(ContextRefreshedEvent.class)
    void contextRefreshedEvent() {
        System.out.println("Context Refreshed");

        System.out.println(
                trucksRepository.findAvailableTrucks(
                        createDate("15/12/2016 08:00"),
                        createDate("16/12/2016 07:00"),
                        500.0
                )
        );

        System.out.println(
                driversRepository.findAvailableDrivers(
                        createDate("15/12/2016 08:00"),
                        createDate("16/12/2016 07:00"),
                        citiesRepository.findByName("Porto Alegre"),
                        400
                )
        );
    }


    private LocalDateTime createDate(String dateString){
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

}
