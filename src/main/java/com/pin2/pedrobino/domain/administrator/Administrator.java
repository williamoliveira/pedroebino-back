package com.pin2.pedrobino.domain.administrator;

import com.pin2.pedrobino.domain.auth.Role;
import com.pin2.pedrobino.domain.person.Person;

import javax.persistence.*;

@Entity
@Table(name = "administrators")
@DiscriminatorValue("ADM")
public class Administrator extends Person implements Role {

    public Administrator() {
    }

    public Administrator(long id, String name) {
        super(id, name);
    }

    public Administrator(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "ADMIN";
    }
}
