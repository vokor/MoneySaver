package com.moneysaver.ExpensePackage;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {
    private String name;
    private double cost;
    private String notes;
    private String category;
    private Date date;

    public Expense(String name, double cost, Date date, String category, String notes){
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.date = date;
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
        return AddExpense.format.format(date);
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

    public void setDate(Date date){
        this.date = date;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }
}
