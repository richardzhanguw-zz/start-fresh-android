package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

/**
 * Created by Richard Zhang on 2017-07-12.
 */

public class FoodType {
    private String foodName;
    private String foodCalories;
    private String perFoodAmount;

    public FoodType(String foodName, String foodCalories, String perFoodAmount){
        this.foodName = foodName;
        this.foodCalories = foodCalories;
        this.perFoodAmount = perFoodAmount;
    }

    public String getFoodCalories(){
        return foodCalories;
    }

    public String getFoodName(){
        return foodName;
    }

    public String getPerFoodAmount(){
        return perFoodAmount;
    }

}
