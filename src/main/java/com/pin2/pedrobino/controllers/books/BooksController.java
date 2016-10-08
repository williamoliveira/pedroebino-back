package com.pin2.pedrobino.controllers.books;

import com.pin2.pedrobino.domain.books.Book;
import com.pin2.pedrobino.domain.books.BooksRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class BooksController {

    private final BooksRepository booksRepository;

    @Inject
    public BooksController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GET
    public List<Book> getBooks() {
        return booksRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") long id) {
        Book book = booksRepository.findOne(id);

        if (book == null) throw new WebApplicationException(Response.Status.NOT_FOUND);

        return book;
    }

    @POST
    public Book saveBook(@Valid Book book) {
        return booksRepository.save(book);
    }

    @DELETE
    @Path("/{id}")
    public void deleteBook(@PathParam("id") long id) {
        booksRepository.delete(id);
    }
}
