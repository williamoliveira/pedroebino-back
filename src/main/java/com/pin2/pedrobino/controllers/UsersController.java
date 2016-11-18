package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.user.User;
import com.pin2.pedrobino.domain.user.UsersRepository;
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
@RequestMapping("/users")
public class UsersController extends ResourceController<User> {

    @Inject
    public UsersController(UsersRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<User> getMany(@QuerydslPredicate(root = User.class) Predicate predicate,
                                  Pageable pageable,
                                  @RequestParam MultiValueMap<String, String> parameters) {
        return super.getMany(predicate, pageable, parameters);
    }
}