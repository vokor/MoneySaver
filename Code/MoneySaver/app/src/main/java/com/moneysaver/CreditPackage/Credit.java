package com.moneysaver.CreditPackage;

import java.io.Serializable;

public class Credit implements Serializable {
    private String name;
    private double allSum;
    private double payout;
    private String notes;

    public Credit(String name, double allSum, double payout, String notes) {
        this.name = name;
        this.allSum = allSum;
        this.payout = payout;
        this.notes = notes;
    }

    public Credit(String name, double allSum, double payout) {
        this.name = name;
        this.allSum = allSum;
        this.payout = payout;
        this.notes = "";
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public Double getAllSum() {
        return allSum;
    }

    public Double getPayout() {
        return payout;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setAllSum(Double allSum) {
        this.allSum = allSum;
    }

    public void setPayout(Double payout) {
        this.payout = payout;
    }

    public void save (Double summ){
        payout = payout + summ;
    }
}