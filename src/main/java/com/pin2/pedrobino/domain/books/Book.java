package com.pin2.pedrobino.domain.books;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pin2.pedrobino.support.persistence.converters.LocalDateConverter;
import com.pin2.pedrobino.support.persistence.converters.LocalDateTimeConverter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 255)
    @NotNull
    private String title;

    @Length(max = 100)
    @NotNull
    private String author;

    @Length(max = 100)
    @NotNull
    private String publisher;

    @Length(max = 20)
    @NotNull
    private String isbn;

    @NotNull
    @Convert(converter = LocalDateConverter.class)
    private LocalDate publicationDate;

    @JsonIgnore
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime updated;

    public Book() {
    }

    public Book(String title, String author, String publisher, String isbn, LocalDate publicationDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
    }

    @PreUpdate
    public void preUpdate() {
        setUpdated(LocalDateTime.now(ZoneOffset.UTC));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
