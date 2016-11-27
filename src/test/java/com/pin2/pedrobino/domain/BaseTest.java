package com.pin2.pedrobino.domain;

import com.pin2.pedrobino.domain.city.CitiesRepository;
import com.pin2.pedrobino.domain.city.City;
import com.pin2.pedrobino.domain.city.State;
import com.pin2.pedrobino.domain.city.StatesRepository;
import com.pin2.pedrobino.domain.client.Client;
import com.pin2.pedrobino.domain.client.ClientsRepository;
import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.driver.DriversRepository;
import com.pin2.pedrobino.domain.proposal.ProposalsRepository;
import com.pin2.pedrobino.domain.request.Request;
import com.pin2.pedrobino.domain.request.RequestFactory;
import com.pin2.pedrobino.domain.request.RequestsRepository;
import com.pin2.pedrobino.domain.settings.Settings;
import com.pin2.pedrobino.domain.settings.SettingsService;
import com.pin2.pedrobino.domain.user.User;
import com.pin2.pedrobino.domain.user.UsersRepository;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

import static com.pin2.pedrobino.support.DateUtil.createDate;

public abstract class BaseTest {

    @Autowired
    protected UsersRepository usersRepository;

    @Autowired
    protected ClientsRepository clientsRepository;

    @Autowired
    protected StatesRepository statesRepository;

    @Autowired
    protected CitiesRepository citiesRepository;

    @Autowired
    protected DriversRepository driversRepository;

    @Autowired
    protected TrucksRepository trucksRepository;

    @Autowired
    protected ProposalsRepository proposalsRepository;

    @Autowired
    protected RequestsRepository requestsRepository;

    @Autowired
    protected RequestFactory requestFactory;

    @Autowired
    protected SettingsService settingsService;

    protected Date START_DATE = createDate("17/12/2016 00:00");

    protected Client CLIENT1;

    protected City PORTO_ALEGRE;
    protected City FLORIANOPOLIS;
    protected City CURITIBA;

    @Before
    public void setUp() {
        System.out.println("Setting up");

        settingsService.saveSettings(new Settings(10, 15));

        // Client
        CLIENT1 = new Client("John Doe");
        clientsRepository.save(CLIENT1);

        User clientUser = new User(
                "johndoe@gmail.com",
                (new BCryptPasswordEncoder()).encode("12345678"),
                CLIENT1
        );
        usersRepository.save(clientUser);

        // States
        State sc = new State("SC", "Santa Catarina");
        State pr = new State("PR", "Paraná");
        State rs = new State("RS", "Rio Grande do Sul");
        statesRepository.save(sc);
        statesRepository.save(rs);
        statesRepository.save(pr);

        FLORIANOPOLIS = new City("Florianópolis", sc);
        citiesRepository.save(FLORIANOPOLIS);

        CURITIBA = new City("Curitiba", pr);
        citiesRepository.save(CURITIBA);

        PORTO_ALEGRE = new City("Porto Alegre", rs);
        citiesRepository.save(PORTO_ALEGRE);

        // Drivers
        Driver driver = new Driver("João Machado", 25 * 365, 30, 3000, "D", PORTO_ALEGRE);
        driversRepository.save(driver);
        Driver driver2 = new Driver("John Doe", 10 * 365, 20, 5000, "E", CURITIBA);
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
                START_DATE,
                300,
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                CLIENT1
        ));

        request.setChosenProposal(request.getProposals().get(1));

        requestsRepository.save(request);
    }

    @After
    public void tearDown() {
        System.out.println("Tearing down");
    }

}
