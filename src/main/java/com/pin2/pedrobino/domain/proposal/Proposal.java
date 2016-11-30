package com.pin2.pedrobino.domain.proposal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.request.Request;
import com.pin2.pedrobino.domain.truck.Truck;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "leaves_at")
    private Date leavesAt;

    @NotNull
    @Column(name = "arrives_at")
    private Date arrivesAt;

    private double value;

    @Column(name = "shared_value")
    private double sharedValue;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "truck_id", nullable = false)
    private Truck truck;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "proposals_drivers",
            joinColumns = {@JoinColumn(name = "proposal_id")},
            inverseJoinColumns = {@JoinColumn(name = "driver_id")}
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Driver> drivers;

    @JsonIgnore
    @ManyToMany(mappedBy = "proposals", fetch = FetchType.EAGER)
    private List<Request> requests;

    @JsonIgnore
    @OneToMany(mappedBy = "chosenProposal", fetch = FetchType.LAZY)
    private List<Request> definedRequests;

    public Proposal() {
    }

    public Proposal(Date leavesAt, Date arrivesAt, double value, Truck truck, List<Driver> drivers, List<Request> requests) {
        this.leavesAt = leavesAt;
        this.arrivesAt = arrivesAt;
        this.value = value;
        this.truck = truck;
        this.drivers = drivers;
        this.requests = requests;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getLeavesAt() {
        return leavesAt;
    }

    public void setLeavesAt(Date leavesAt) {
        this.leavesAt = leavesAt;
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

    public Date getArrivesAt() {
        return arrivesAt;
    }

    public void setArrivesAt(Date arrivesAt) {
        this.arrivesAt = arrivesAt;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void addRequest(Request request) {
        if (getRequests() == null) {
            setRequests(new ArrayList<>());
        }

        getRequests().add(request);
    }

    public List<Request> getDefinedRequests() {
        return definedRequests;
    }

    public void setDefinedRequests(List<Request> definedRequests) {
        this.definedRequests = definedRequests;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", leavesAt=" + leavesAt +
                ", arrivesAt=" + arrivesAt +
                ", value=" + value +
                ", truck=" + truck +
                ", drivers=" + drivers +
                ", requests=" + requests +
                ", definedRequests=" + definedRequests +
                '}';
    }

    public double getSharedValue() {
        return sharedValue;
    }

    public void setSharedValue(double sharedValue) {
        this.sharedValue = sharedValue;
    }
}
