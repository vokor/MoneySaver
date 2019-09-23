package com.moneysaver.ExpensePackage;

import com.moneysaver.Config;

import java.io.Serializable;
import java.util.Date;

import static com.moneysaver.Config.setSpent;

public class Expense implements Serializable {
    private String name;
    private double cost;
    private String notes;
    private String category;
    private Date date;
    private int id;

    public Expense(String name, double cost, Date date, String category, String notes){
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.date = date;
        this.notes = notes;
        id = Config.getId();
    }

    public Expense(String name, double cost, Date date, String category, String notes, int id){
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.date = date;
        this.notes = notes;
        this.id = id;
    }

    public int getId() {return id;}

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
        this.cost = setSpent(cost);
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
