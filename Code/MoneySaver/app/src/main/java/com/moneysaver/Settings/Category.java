package com.moneysaver.Settings;

import static com.moneysaver.Config.getId;

public class Category {
    private String name;
    private int maxSum;
    private double spent;
    public boolean approved;
    public boolean changed;
    public boolean deleted;
    private int id;

    public Category(String name, int maxSum, double spent){
        this.name = name;
        this.spent = spent;
        this.maxSum = maxSum;
        approved = true;
        changed = false;
        deleted = false;
        id = getId();
    }

    public Category(String name, int maxSum, double spent, int id) {
        this.name = name;
        this.maxSum = maxSum;
        this.id = id;
        approved = true;
        this.spent = spent;
    }

    public double getBalance() {
        return this.maxSum - this.spent;
    }

    public void setMaxSum(int maxSum) {
        this.maxSum = maxSum;
    }

    public int getMaxSum() {
        return this.maxSum;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getSpent() {
        return spent;
    }

    public String getName(){
        return this.name;
    }

    public int getId() {
        return id;
    }

    public boolean checkNames(Category category) {
        return name.equals(category.getName());
    }
}
