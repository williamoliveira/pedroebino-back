package com.pin2.pedrobino.domain.city;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "states")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView
    private long id;

    @NotEmpty
    @Column(nullable = false, length = 2)
    private String initials;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<City> cities;

    public State() {
    }

    public State(String initials, String name) {
        this.initials = initials;
        this.name = name;
    }

    public State(String initials, String name, List<City> cities) {
        this.initials = initials;
        this.name = name;
        this.cities = cities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
