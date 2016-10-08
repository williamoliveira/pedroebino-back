package com.pin2.pedrobino.controllers.users;

import com.pin2.pedrobino.domain.auth.UsersRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Path("/users/{userId}/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class UsersBooksController {
    private final UsersRepository usersRepository;

    @Inject
    public UsersBooksController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

//    @GET
//    public List<Book> getUsersBooks(@PathParam("userId") long userId) {
//        User user = usersRepository.findOne(userId);
//
//        if (user == null) {
//            throw new WebApplicationException(Response.Status.NOT_FOUND);
//        }
//
//        return user.getBooks();
//    }
}
