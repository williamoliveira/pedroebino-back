package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

abstract public class ResourceController<T> {

    private JpaRepository<T, Long> repository;

    public ResourceController(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @RequestMapping
    public Page<T> getMany(
            @RequestParam(required=false, defaultValue="1") Integer page,
            @RequestParam(required=false, defaultValue="15") Integer pageSize,
            @RequestParam(required=false, defaultValue="DESC") String sort
    ) {

        PageRequest pageRequest = new PageRequest(
                page-1,
                pageSize,
                Sort.Direction.fromString(sort),
                "id"
        );

        return repository.findAll(pageRequest);
    }

    @RequestMapping("/{id}")
    public T getById(@PathVariable long id) {
        T resource = repository.findOne(id);

        if(resource == null) throw new ResourceNotFoundException();

        return resource;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    @ResponseStatus(HttpStatus.OK)
    public T create(@RequestBody T resource) {
        return repository.save(resource);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public T update(@Valid T resource) {
        return repository.save(resource);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        repository.delete(id);
    }
}
