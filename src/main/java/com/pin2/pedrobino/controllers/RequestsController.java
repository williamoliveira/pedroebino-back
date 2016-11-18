package com.pin2.pedrobino.controllers;

import com.pin2.pedrobino.domain.client.Client;
import com.pin2.pedrobino.domain.client.QClient;
import com.pin2.pedrobino.domain.request.*;
import com.pin2.pedrobino.domain.user.User;
import com.querydsl.core.types.ExpressionUtils;
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
public class RequestsController extends ResourceController<Request> {

    @Autowired
    private RequestFactory requestFactory;

    @Autowired
    private ProposalsRepository proposalsRepository;

    @Inject
    public RequestsController(RequestsRepository repository) {
        super(repository);
    }

    @Override
    public Iterable<Request> getMany(@QuerydslPredicate(root = Request.class) Predicate predicate,
                                     Pageable pageable,
                                     @RequestParam MultiValueMap<String, String> parameters) {
        User user = getCurrentUser();

        if (user.getPerson() instanceof Client) {
            predicate = ExpressionUtils.allOf(
                    predicate,
                    QClient.client.eq((Client) user.getPerson())
            );
        }

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
        resource.setClient((Client) getCurrentUser().getPerson());

        Request request = requestFactory.create(resource);

        return repository.save(request);
    }

    @RequestMapping(
            path = "/{requestId}/choose-proposal",
            method = RequestMethod.POST,
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public Request chooseProposal(@PathVariable long requestId,
                                  @RequestBody Proposal proposal) {
        Request request = repository.findOne(requestId);
        proposal = proposalsRepository.findOne(proposal.getId());

        request.setChosenProposal(proposal);
        request.setStatus(RequestStatus.DEFINED);

        return repository.save(request);
    }

    @RequestMapping(
            path = "/{requestId}/cancel",
            method = RequestMethod.POST
    )
    public Request cancelRequest(@PathVariable long requestId) {
        Request request = repository.findOne(requestId);

        request.setStatus(RequestStatus.CANCELED);

        return repository.save(request);
    }
}