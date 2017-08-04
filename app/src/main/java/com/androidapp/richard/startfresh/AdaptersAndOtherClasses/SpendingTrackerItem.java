package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

/**
 * Created by zhang on 2017-08-03.
 */

public class SpendingTrackerItem {
    private Double changeInBalance;
    private String name;

    public SpendingTrackerItem (Double changeInBalance, String name) {
        this.changeInBalance = changeInBalance;
        this.name = name;
    }

    public Double getChangeInBalance() {
        return changeInBalance;
    }

    public String getName() {
        return name;
    }
}
