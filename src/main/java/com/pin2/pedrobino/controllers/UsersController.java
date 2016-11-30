package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.administrator.Administrator;
import com.pin2.pedrobino.domain.client.Client;
import com.pin2.pedrobino.domain.person.Person;
import com.pin2.pedrobino.domain.user.User;
import com.pin2.pedrobino.domain.user.UsersRepository;
import com.pin2.pedrobino.support.PasswordHasher;
import com.pin2.pedrobino.support.exceptions.ResourceNotFoundException;
import com.querydsl.core.types.Predicate;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Transactional
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    protected UsersRepository repository;

    @RequestMapping
    public Iterable<User> getMany(@QuerydslPredicate Predicate predicate,
                                  Pageable pageable,
                                  @RequestParam MultiValueMap<String, String> parameters) {

        Iterable<User> users = shouldPaginate(parameters)
                ? repository.findAll(predicate, pageable)
                : repository.findAll(predicate, pageable.getSort());

        users.forEach(user -> Hibernate.initialize(user.getPerson()));

        return users;
    }

    @RequestMapping("/{id}")
    public User getById(@PathVariable long id) {
        User user = repository.findOne(id);

        if (user == null) throw new ResourceNotFoundException();

        Hibernate.initialize(user.getPerson());

        return user;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public User create(@RequestBody UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(PasswordHasher.hash(userRequest.getPassword()));

        Person person = userRequest.getRole().equalsIgnoreCase("ADMIN")
                ? new Administrator()
                : new Client();
        person.setName(userRequest.getName());

        user.setPerson(person);


        return repository.save(user);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public User update(@PathVariable(name = "id") long id, @Valid UserRequest userRequest) {
        User user = repository.findOne(id);
        user.setEmail(userRequest.getEmail());
        user.getPerson().setName(userRequest.getName());

        return repository.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        repository.delete(id);
    }

    private boolean shouldPaginate(MultiValueMap<String, String> parameters) {
        return !parameters.containsKey("paginate")
                || Boolean.parseBoolean(parameters.getFirst("paginate"));

    }

    private static class UserRequest {
        private String name;
        private String email;
        private String role;
        private String password;

        public UserRequest() {
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

}