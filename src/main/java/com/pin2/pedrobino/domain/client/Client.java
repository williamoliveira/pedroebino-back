package com.pin2.pedrobino.domain.client;

import com.pin2.pedrobino.domain.auth.Role;
import com.pin2.pedrobino.domain.person.Person;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@DiscriminatorValue("CLI")
public class Client extends Person implements Role {

    public Client() {
    }

    public Client(long id, String name) {
        super(id, name);
    }

    public Client(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "CLIENT";
    }
}
