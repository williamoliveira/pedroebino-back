package com.pin2.pedrobino.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pin2.pedrobino.domain.administrator.Administrator;
import com.pin2.pedrobino.domain.client.Client;
import com.pin2.pedrobino.domain.person.Person;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@DiscriminatorValue("USR")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotEmpty
    private String password;

    @Any(
            metaColumn = @Column(name = "person_type", length = 3),
            fetch = FetchType.EAGER,
            optional = false
    )
    @AnyMetaDef(
            idType = "long",
            metaType = "string",
            metaValues = {
                    @MetaValue(targetEntity = Administrator.class, value = "ADM"),
                    @MetaValue(targetEntity = Client.class, value = "CLI")
            }
    )
    @JoinColumn(name = "person_id")
    @Cascade(CascadeType.ALL)
    private Person person;

    public User() {
    }

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.person = user.getPerson();
    }

    public User(String email, String password, Person person) {
        this.email = email;
        this.password = password;
        this.person = person;
    }

    public User(long id, String email, String password, Person person) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.person = person;
    }

    public String getRole() {
        if (getPerson() instanceof Administrator) {
            return "ADMIN";
        }

        return "CLIENT";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
