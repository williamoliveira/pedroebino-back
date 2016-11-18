package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.truck.Truck;
import com.pin2.pedrobino.domain.truck.TrucksRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@Transactional
@RestController
@RequestMapping("/trucks")
public class TrucksController extends ResourceController<Truck> {

    @Inject
    public TrucksController(TrucksRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<Truck> getMany(@QuerydslPredicate(root = Truck.class) Predicate predicate,
                                   Pageable pageable,
                                   @RequestParam MultiValueMap<String, String> parameters) {
        return super.getMany(predicate, pageable, parameters);
    }

}