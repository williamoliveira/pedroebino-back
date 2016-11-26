package com.pin2.pedrobino.domain.settings;


import javax.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "profit_margin")
    private double profitMargin;

    @Column(name = "shared_discount")
    private double sharedDiscount;

    public Settings() {
    }

    public Settings(double profitMargin, double sharedDiscount) {
        this.profitMargin = profitMargin;
        this.sharedDiscount = sharedDiscount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public double getSharedDiscount() {
        return sharedDiscount;
    }

    public void setSharedDiscount(double sharedDiscount) {
        this.sharedDiscount = sharedDiscount;
    }
}
