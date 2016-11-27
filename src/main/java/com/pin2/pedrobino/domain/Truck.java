package com.pin2.pedrobino.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pin2.pedrobino.domain.proposal.Proposal;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String license;

    @NotNull
    @Min(0)
    private int volume;

    @NotNull
    @Min(0)
    @Column(name = "cost_per_km", nullable = false, precision = 2)
    private double costPerKm;

    @JsonIgnore
    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY)
    private List<Proposal> proposals;

    public Truck() {
    }

    public Truck(String name, String license, int volume, double costPerKm) {
        this.name = name;
        this.license = license;
        this.volume = volume;
        this.costPerKm = costPerKm;
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

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public double getCostPerKm() {
        return costPerKm;
    }

    public void setCostPerKm(double costPerKm) {
        this.costPerKm = costPerKm;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", license='" + license + '\'' +
                ", volume=" + volume +
                ", costPerKm=" + costPerKm +
                '}';
    }
}
