package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

/**
 * Created by zhang on 2017-07-12.
 */

public class FoodType {
    private String foodName;
    private String foodCalories;

    public FoodType(String foodName, String foodCalories){
        this.foodName = foodName;
        this.foodCalories = foodCalories;
    }

    public String getFoodCalories(){
        return foodCalories;
    }

    public String getFoodName(){
        return foodName;
    }
}
