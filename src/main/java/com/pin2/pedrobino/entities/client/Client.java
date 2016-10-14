package com.pin2.pedrobino.entities.client;

import com.pin2.pedrobino.entities.person.Person;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@DiscriminatorValue("CLI")
public class Client extends Person {

    public Client() {
    }

    public Client(long id, String name) {
        super(id, name);
    }

    public Client(String name) {
        super(name);
    }
}
