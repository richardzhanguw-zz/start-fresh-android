package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidapp.richard.startfresh.R;

import java.util.ArrayList;

/**
 * Created by zhang on 2017-07-12.
 */

public class FoodTypesArrayAdapter extends ArrayAdapter<FoodType> {
    Context context;
    ArrayList<FoodType> foods;
    public FoodTypesArrayAdapter(Context context, ArrayList<FoodType> foods){
        super(context, -1, foods);
        this.context = context;
        this.foods = foods;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.food_type_row_view, parent, false);
        final RelativeLayout expandableView = (RelativeLayout) rowView.findViewById(R.id.add_intake_amount_relative_view);
        expandableView.setTag("expandableview"+String.valueOf(position));
        final EditText intakeAmount = (EditText) rowView.findViewById(R.id.food_amount_edit_text);
        final TextView foodCalorie = (TextView) rowView.findViewById(R.id.food_calorie_amount);
        intakeAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    foodCalorie.setText(String.valueOf((Integer.parseInt(intakeAmount.getText().toString())/Integer.parseInt(foods.get(position).getPerFoodAmount().substring(0,3)))*Integer.parseInt(foods.get(position).getFoodCalories())));
                }
                expandableView.setVisibility(View.GONE);
                return false;
            }
        });
        TextView foodName = (TextView) rowView.findViewById(R.id.food_name_text_view);
        foodName.setText(foods.get(position).getFoodName());
        foodCalorie.setText(foods.get(position).getFoodCalories()+"calories/"+foods.get(position).getPerFoodAmount());
        return rowView;
    }
}
