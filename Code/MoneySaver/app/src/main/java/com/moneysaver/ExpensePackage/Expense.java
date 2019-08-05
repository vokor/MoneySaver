package com.moneysaver.ExpensePackage;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {
    private String name;
    private double cost;
    private String notes;
    private String category;
    private int day;
    private int month;
    private int year;

    public Expense(String name, double cost, String category, int day, int month, int year, String notes){
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.day = day;
        this.month = month;
        this.year = year;
        this.notes = notes;
    }

    public String getName(){
        return name;
    }

    public Double getCost(){
        return cost;
    }

    public String getCategory(){
        return category;
    }

    public String getDate(){
        String str = day + "." + month + "." + year;
        return str;
    }

    public String getNotes(){
        return notes;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCost(Double cost){
        this.cost = cost;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setDate(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }
}
