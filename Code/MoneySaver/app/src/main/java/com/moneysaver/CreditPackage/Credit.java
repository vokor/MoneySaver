package com.moneysaver.CreditPackage;

import java.io.Serializable;

public class Credit implements Serializable {
    private String name;
    private double cost;
    private double saved;
    private String notes;

    public Credit(String name, double cost, String notes) {
        this.name = name;
        this.cost = cost;
        this.saved = 0;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public Double getCost() {
        return cost;
    }

    public Double getSaved() {
        return saved;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public void setSaved(Double saved) {
        this.saved = saved;
    }

    public void save (Double summ){
        saved = saved + summ;
    }
}