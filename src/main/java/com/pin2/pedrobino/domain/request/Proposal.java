package com.pin2.pedrobino.domain.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.truck.Truck;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "leaves_at")
    private LocalDateTime leavesAt;

    @NotNull
    @Column(name = "arrives_at")
    private LocalDateTime arrivesAt;

    private double value;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "truck_id", nullable = false)
    private Truck truck;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "proposals_drivers",
            joinColumns = {@JoinColumn(name = "proposal_id")},
            inverseJoinColumns = {@JoinColumn(name = "driver_id")}
    )
    private List<Driver> drivers;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    public Proposal() {
    }

    public Proposal(LocalDateTime leavesAt,
                    LocalDateTime arrivesAt,
                    Truck truck,
                    List<Driver> drivers,
                    Request request) {
        this.leavesAt = leavesAt;
        this.arrivesAt = arrivesAt;
        this.truck = truck;
        this.drivers = drivers;
        this.request = request;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getLeavesAt() {
        return leavesAt;
    }

    public void setLeavesAt(LocalDateTime leavesAt) {
        this.leavesAt = leavesAt;
    }


    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public LocalDateTime getArrivesAt() {
        return arrivesAt;
    }

    public void setArrivesAt(LocalDateTime arrivesAt) {
        this.arrivesAt = arrivesAt;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


}
