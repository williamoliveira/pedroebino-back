package com.pin2.pedrobino.entities.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pin2.pedrobino.entities.driver.Driver;
import com.pin2.pedrobino.entities.truck.Truck;
import com.pin2.pedrobino.services.ConvertTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="truck_id", nullable=false)
    private Truck truck;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name="proposals_drivers",
            joinColumns={@JoinColumn(name="proposal_id")},
            inverseJoinColumns={@JoinColumn(name="driver_id")}
    )
    private List<Driver> drivers;

    @JsonIgnore
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="request_id", nullable=false)
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

    @JsonSerialize
    public double getValue() {
        double value = 0;

        for(Driver driver : drivers){
            value += driver.getHourlyWage() * ConvertTime.fromSecondsToHours(request.getEstimatedTravelDuration());
        }

        return value;
    }
}
