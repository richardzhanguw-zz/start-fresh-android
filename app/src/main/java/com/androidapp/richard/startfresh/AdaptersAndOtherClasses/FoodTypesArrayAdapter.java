package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.richard.startfresh.R;

/**
 * Created by zhang on 2017-07-12.
 */

public class FoodTypesArrayAdapter extends ArrayAdapter<FoodType> {
    Context context;
    FoodType[] foods;
    public FoodTypesArrayAdapter(Context context, FoodType[] foods){
        super(context, -1, foods);
        this.context = context;
        this.foods = foods;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.food_type_row_view, parent, false);
        TextView foodName = (TextView) rowView.findViewById(R.id.food_name_text_view);
        TextView foodCalorie = (TextView) rowView.findViewById(R.id.food_calorie_amount);
        foodName.setText(foods[position].getFoodName());
        foodCalorie.setText(foods[position].getFoodCalories());
        return rowView;
    }
}
