package com.pin2.pedrobino.entities.administrator;

import com.pin2.pedrobino.entities.person.Person;

import javax.persistence.*;

@Entity
@Table(name = "administrators")
@DiscriminatorValue("ADM")
public class Administrator extends Person {

    public Administrator() {
    }

    public Administrator(long id, String name) {
        super(id, name);
    }

    public Administrator(String name) {
        super(name);
    }

}
