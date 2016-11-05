package com.pin2.pedrobino.entities.city;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="state_id", nullable=false)
    private State state;

    public City() {
    }

    public City(long id, String name, State state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
