package com.pin2.pedrobino.entities.driver;

import com.pin2.pedrobino.entities.city.City;
import com.pin2.pedrobino.entities.person.Person;
import com.pin2.pedrobino.entities.request.Proposal;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver extends Person {

    // In days
    private long experience = 0;

    // In R$
    @Column(name = "hourly_wage", precision = 2)
    private double hourlyWage;

    // In meters
    @Column(name = "max_distance", nullable = true)
    private long maxDistance;

    @NotEmpty
    private String license;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="city_id")
    private City city;

    public Driver() {
    }

    public Driver(String name,
                  long experience,
                  double hourlyWage,
                  long maxDistance,
                  String license,
                  City city) {
        super(name);
        this.experience = experience;
        this.hourlyWage = hourlyWage;
        this.maxDistance = maxDistance;
        this.license = license;
        this.city = city;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(long hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(long maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
