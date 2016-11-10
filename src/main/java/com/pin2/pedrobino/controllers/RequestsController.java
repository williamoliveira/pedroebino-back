package com.pin2.pedrobino.controllers;

import com.google.maps.model.DistanceMatrixElement;
import com.pin2.pedrobino.entities.city.CitiesRepository;
import com.pin2.pedrobino.entities.city.StatesRepository;
import com.pin2.pedrobino.entities.client.Client;
import com.pin2.pedrobino.entities.request.Request;
import com.pin2.pedrobino.entities.request.RequestsRepository;
import com.pin2.pedrobino.support.distance.DistanceCalculator;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@Transactional
@RestController
@RequestMapping("/requests")
public class RequestsController extends ResourceController<Request>{

    @Autowired
    private DistanceCalculator distanceCalculator;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private StatesRepository statesRepository;

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

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    @ResponseStatus(HttpStatus.OK)
    public Request create(@RequestBody Request resource) {

        resource.setFrom(citiesRepository.findOne(resource.getFrom().getId()));
        resource.getFrom().setState(statesRepository.findOne(resource.getFrom().getState().getId()));
        resource.setTo(citiesRepository.findOne(resource.getTo().getId()));
        resource.getTo().setState(statesRepository.findOne(resource.getTo().getState().getId()));

        resource.setClient((Client)getCurrentUser().getPerson());

        resource.setStatus("pending");

        try {

            DistanceMatrixElement distanceMatrixElement = distanceCalculator.calculate(
                    resource.getFrom(),
                    resource.getTo()
            );

            resource.setEstimatedTravelDuration(distanceMatrixElement.duration.inSeconds);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return repository.save(resource);
    }
}