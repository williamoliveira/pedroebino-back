package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.city.CitiesRepository;
import com.pin2.pedrobino.domain.city.StatesRepository;
import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.driver.DriversRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@Transactional
@RestController
@RequestMapping("/drivers")
public class DriversController extends ResourceController<Driver> {

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private StatesRepository statesRepository;

    @Inject
    public DriversController(DriversRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<Driver> getMany(@QuerydslPredicate(root = Driver.class) Predicate predicate,
                                    Pageable pageable,
                                    @RequestParam MultiValueMap<String, String> parameters) {
        return super.getMany(predicate, pageable, parameters);
    }

    @Override
    public Driver create(@RequestBody Driver resource) {

        resource.setCity(citiesRepository.findOne(resource.getCity().getId()));
        resource.getCity().setState(statesRepository.findOne(resource.getCity().getState().getId()));

        return super.create(resource);
    }
}