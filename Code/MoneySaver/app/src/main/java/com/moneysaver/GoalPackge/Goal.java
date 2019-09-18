package com.moneysaver.GoalPackge;

import com.moneysaver.Config;

import java.io.Serializable;

public class Goal implements Serializable {
    private int id;
    private String name;
    private double cost;
    private double saved;
    private String notes;

    public Goal(String name, double cost, double saved, String notes) {
        id = Config.getId();
        this.name = name;
        this.cost = cost;
        this.saved = saved;
        this.notes = notes;
    }

    public Goal(String name, double cost, double saved) {
        id = Config.getId();
        this.name = name;
        this.cost = cost;
        this.saved = saved;
        this.notes = "";
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

    public int getId() { return id;}

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