package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.entities.city.State;
import com.pin2.pedrobino.entities.city.StatesRepository;
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
@RequestMapping("/states")
public class StatesController extends ResourceController<State>{

    @Inject
    public StatesController(StatesRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<State> getMany(@QuerydslPredicate(root=State.class) Predicate predicate,
                                   Pageable pageable,
                                   @RequestParam MultiValueMap<String, String> parameters) {
        return super.getMany(predicate, pageable, parameters);
    }
}