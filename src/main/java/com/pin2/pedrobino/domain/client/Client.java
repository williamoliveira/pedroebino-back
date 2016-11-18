package com.pin2.pedrobino.domain.client;

import com.pin2.pedrobino.domain.person.Person;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

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
