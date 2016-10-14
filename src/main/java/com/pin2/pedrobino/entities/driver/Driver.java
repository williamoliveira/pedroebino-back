package com.pin2.pedrobino.entities.driver;

import com.pin2.pedrobino.entities.city.City;
import com.pin2.pedrobino.entities.person.Person;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver extends Person {

    private double experience = 0;

    @Column(name = "hourly_wage")
    private double hourlyWage;

    @Column(name = "max_distance", nullable = true)
    private double maxDistance;

    @NotEmpty
    private String license;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="city_id")
    private City city;

    public Driver() {
    }

    public Driver(String name, double experience, double hourlyWage, double maxDistance, String license, City city) {
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

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
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
