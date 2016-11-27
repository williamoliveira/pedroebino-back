package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.Truck;
import com.pin2.pedrobino.domain.TrucksRepository;
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
import com.pin2.pedrobino.domain.user.User;
import com.pin2.pedrobino.domain.user.UsersRepository;
import com.pin2.pedrobino.support.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

//        System.out.println(
//                trucksRepository.findAvailableTrucks(
//                        createDate("17/12/2016 01:00"),
//                        createDate("17/12/2016 07:00"),
//                        100.0
//                )
//        );
//
//        System.out.println(
//                trucksRepository.findAvailableTrucks(
//                        createDate("20/12/2016 00:00"),
//                        createDate("20/12/2016 07:00"),
//                        100.0
//                )
//        );

//        System.out.println("0");
//        System.out.println(
//                driversRepository.findAvailableDrivers(
//                        createDate("17/12/2016 00:00"),
//                        createDate("17/12/2016 07:00"),
//                        citiesRepository.findByName("Porto Alegre"),
//                        400
//                )
//        );
//
//        System.out.println("1");
//        System.out.println(
//                driversRepository.findAvailableDrivers(
//                        createDate("20/12/2016 00:00"),
//                        createDate("20/12/2016 07:00"),
//                        citiesRepository.findByName("Porto Alegre"),
//                        400
//                )
//        );

//
//        Request request = requestsRepository.findAll().get(0);
//
//        List<Proposal> proposals = proposalsCalculator.calculateProposals(request);
//
//        System.out.println(proposals);

        Proposal proposal = proposalsRepository.findBestShareableProposal(
                DateUtil.createDate("17/12/2016 00:00"),
                citiesRepository.findByName("Porto Alegre"),
                citiesRepository.findByName("Florianópolis"),
                100
        );

        System.out.println("Proposals 1:");
        System.out.println(proposal);

        proposal = proposalsRepository.findBestShareableProposal(
                DateUtil.createDate("20/12/2016 00:00"),
                citiesRepository.findByName("Porto Alegre"),
                citiesRepository.findByName("Florianópolis"),
                100
        );

        System.out.println("Proposals 0:");
        System.out.println(proposal);
    }

}
