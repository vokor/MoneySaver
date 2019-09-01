package com.moneysaver.Settings;

import static com.moneysaver.Config.getId;

public class Category {
    private String name;
    private int maxSum;
    private int spent;
    public boolean approved;
    public boolean changed;
    public boolean deleted;
    private int id;

    public Category(String name, int maxSum){
        this.name = name;
        this.spent= 0;
        this.maxSum = maxSum;
        approved = true;
        changed = false;
        deleted = false;
        id = getId();
    }

    public int getBalance() {
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
