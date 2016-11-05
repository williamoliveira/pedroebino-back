package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.entities.agenda.AgendaEntriesRepository;
import com.pin2.pedrobino.entities.agenda.AgendaEntry;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Transactional
@RestController
@RequestMapping("/agenda-entries")
public class AgendaEntriesController extends ResourceController<AgendaEntry>{

    @Inject
    public AgendaEntriesController(AgendaEntriesRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<AgendaEntry> getMany(@QuerydslPredicate(root=AgendaEntry.class) Predicate predicate,
                                  Pageable pageable,
                                  @RequestParam MultiValueMap<String, String> parameters) {
        return super.getMany(predicate, pageable, parameters);
    }
}