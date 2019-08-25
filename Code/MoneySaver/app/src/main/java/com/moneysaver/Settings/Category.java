package com.moneysaver.Settings;

public class Category {
    private String name;
    private int maxSum;
    private int spent;

    public Category(String name, int maxSum){
        this.name = name;
        this.spent= 0;
        this.maxSum = maxSum;
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
}
