package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.entities.request.Request;
import com.pin2.pedrobino.entities.request.RequestsRepository;
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
@RequestMapping("/requests")
public class RequestsController extends ResourceController<Request>{

    @Inject
    public RequestsController(RequestsRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<Request> getMany(@QuerydslPredicate(root=Request.class) Predicate predicate,
                                    Pageable pageable,
                                    @RequestParam MultiValueMap<String, String> parameters) {
        return super.getMany(predicate, pageable, parameters);
    }

}