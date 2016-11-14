package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.entities.user.User;
import com.pin2.pedrobino.support.exceptions.ResourceNotFoundException;
import com.pin2.pedrobino.support.repository.BaseRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

abstract public class ResourceController<T> {

    protected BaseRepository<T> repository;

    public ResourceController(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @RequestMapping
    public Iterable<T> getMany(@QuerydslPredicate Predicate predicate,
                               Pageable pageable,
                               @RequestParam MultiValueMap<String, String> parameters) {

        return shouldPaginate(parameters)
                ? repository.findAll(predicate, pageable)
                : repository.findAll(predicate, pageable.getSort());
    }

    @RequestMapping("/{id}")
    public T getById(@PathVariable long id) {
        T resource = repository.findOne(id);

        if(resource == null) throw new ResourceNotFoundException();

        return resource;
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
    public T create(@RequestBody T resource) {
        return repository.save(resource);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public T update(@Valid T resource) {
        return repository.save(resource);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        repository.delete(id);
    }

    private boolean shouldPaginate(MultiValueMap<String, String> parameters) {
        return !parameters.containsKey("paginate")
                || Boolean.parseBoolean(parameters.getFirst("paginate"));

    }

    protected User getCurrentUser(){
        return (User)((OAuth2Authentication) SecurityContextHolder
                .getContext().getAuthentication())
                .getPrincipal();
    }

}
