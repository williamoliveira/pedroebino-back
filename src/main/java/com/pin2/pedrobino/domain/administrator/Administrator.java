package com.pin2.pedrobino.domain.administrator;

import com.pin2.pedrobino.domain.person.Person;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

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
