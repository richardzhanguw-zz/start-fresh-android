package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

/**
 * Created by Richard Zhang on 2017-08-03.
 */

public class SpendingTrackerItem {
    private Double changeInBalance;
    private String name;
    private String date;
    public SpendingTrackerItem (Double changeInBalance, String name, String date) {
        this.changeInBalance = changeInBalance;
        this.name = name;
        this.date = date;
    }

    public Double getChangeInBalance() {
        return changeInBalance;
    }

    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }

}
