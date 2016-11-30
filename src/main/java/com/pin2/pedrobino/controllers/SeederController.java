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
import com.pin2.pedrobino.domain.proposal.Proposal;
import com.pin2.pedrobino.domain.proposal.ProposalsCalculator;
import com.pin2.pedrobino.domain.proposal.ProposalsRepository;
import com.pin2.pedrobino.domain.request.Request;
import com.pin2.pedrobino.domain.request.RequestFactory;
import com.pin2.pedrobino.domain.request.RequestsRepository;
import com.pin2.pedrobino.domain.settings.Settings;
import com.pin2.pedrobino.domain.settings.SettingsService;
import com.pin2.pedrobino.domain.truck.Truck;
import com.pin2.pedrobino.domain.truck.TrucksRepository;
import com.pin2.pedrobino.domain.user.User;
import com.pin2.pedrobino.domain.user.UsersRepository;
import com.pin2.pedrobino.support.DateUtil;
import com.pin2.pedrobino.support.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@RestController
@Transactional
public class SeederController {

    @Inject
    private Environment springEnvironment;

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
    private ProposalsCalculator proposalsCalculator;

    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private SettingsService settingsService;

    @PostConstruct
    public void seed() {

        String environmentName = springEnvironment.getActiveProfiles().length > 0
                ? springEnvironment.getActiveProfiles()[0]
                : "local";

        // Do not seed for test, tests have its own seeds
        if (environmentName.equalsIgnoreCase("test")) return;

        // Already seeded
        if (settingsService.getSettings() != null) return;

        settingsService.saveSettings(new Settings(10, 15));

        // Administrator
        Administrator admin = new Administrator("Administrator");
        User adminUser = new User(
                "admin@admin.com",
                PasswordHasher.hash("12345678"),
                admin
        );
        usersRepository.save(adminUser);

        // Client
        Client client = new Client("John Doe");
        User clientUser = new User(
                "johndoe@gmail.com",
                PasswordHasher.hash("12345678"),
                client);
        usersRepository.save(clientUser);

        // States
        State sc = new State("SC", "Santa Catarina");
        State pr = new State("PR", "Paraná");
        State rs = new State("RS", "Rio Grande do Sul");
        State ac = new State("AC", "Acre");
        statesRepository.save(sc);
        statesRepository.save(rs);
        statesRepository.save(pr);
        statesRepository.save(ac);

        City florianopolis = new City("Florianópolis", sc);
        citiesRepository.save(florianopolis);

        City curitiba = new City("Curitiba", pr);
        citiesRepository.save(curitiba);

        City portoAlegre = new City("Porto Alegre", rs);
        citiesRepository.save(portoAlegre);

        City rioBranco = new City("Rio Branco", ac);
        citiesRepository.save(rioBranco);

        // Drivers
        Driver driver = new Driver("João Machado", 25 * 365, 30, 3000, "D", portoAlegre);
        driversRepository.save(driver);
        Driver driver2 = new Driver("John Doe", 10 * 365, 20, 5000, "E", curitiba);
        driversRepository.save(driver2);

        // Trucks
        Truck truck = new Truck("Volvo 454", "D", 2000, 0.8);
        trucksRepository.save(truck);

        Truck truck2 = new Truck("Volvo 234", "D", 1000, 0.6);
        trucksRepository.save(truck2);

        Truck truck3 = new Truck("Volvo 324", "D", 890, 0.5);
        trucksRepository.save(truck3);


        // Requests
        Request request = requestFactory.create(new Request(
                true,
                DateUtil.createDate("17/12/2016 00:00"),
                300,
                portoAlegre,
                florianopolis,
                client
        ));

        if (request != null && request.getProposals() != null)
            request.setChosenProposal(request.getProposals().get(1));

        requestsRepository.save(request);

//
//        request = requestFactory.create(new Request(
//                true,
//                createDate("17/12/2016 00:00"),
//                100,
//                portoAlegre,
//                florianopolis,
//                client
//        ));
//
//        System.out.println(request.getProposals());

        System.out.println("All seeded");
    }

    //    @EventListener(ContextRefreshedEvent.class)
    void contextRefreshedEvent() {
        System.out.println("Context Refreshed");

    }

}
