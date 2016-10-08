package com.pin2.pedrobino.controllers.users;

import com.pin2.pedrobino.controllers.ResourceController;
import com.pin2.pedrobino.domain.auth.User;
import com.pin2.pedrobino.domain.auth.UsersRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Transactional
@RestController
@RequestMapping("/users")
public class UsersController extends ResourceController<User>{

    @Inject
    public UsersController(UsersRepository repository) {
        super(repository);
    }

}