package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidapp.richard.startfresh.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Richard Zhang on 2017-07-12.
 */

public class FoodTypesArrayAdapter extends ArrayAdapter<FoodType> {
    Context context;
    ArrayList<FoodType> foods;
    CoordinatorLayout coordinatorLayout;
    public FoodTypesArrayAdapter(Context context, ArrayList<FoodType> foods, CoordinatorLayout coordinatorLayout){
        super(context, -1, foods);
        this.context = context;
        this.foods = foods;
        this.coordinatorLayout = coordinatorLayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.food_type_row_view, parent, false);
        final RelativeLayout expandableView = (RelativeLayout) rowView.findViewById(R.id.add_intake_amount_relative_view);
        expandableView.setTag("expandableview"+String.valueOf(position));
        final EditText intakeAmount = (EditText) rowView.findViewById(R.id.food_amount_edit_text);
        final TextView foodCalorie = (TextView) rowView.findViewById(R.id.food_calorie_amount);
        intakeAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date date = new Date();
                    int caloriesConsumed = (Integer.parseInt(intakeAmount.getText().toString())/Integer.parseInt(foods.get(position).getPerFoodAmount().substring(0,3)))*Integer.parseInt(foods.get(position).getFoodCalories());
                    foodCalorie.setText(String.valueOf(caloriesConsumed));
                    SharedPreferences sharedPref = context.getSharedPreferences("caloriecount",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("most recently added entry date", dateFormat.format(date));
                    editor.putInt("current day calorie count", sharedPref.getInt("current day calorie count", 0) + caloriesConsumed);
                    editor.apply();
                    Log.d("calories", String.valueOf(sharedPref.getInt("current day calorie count", 0))+"calories recorded");
                    View view = ((Activity)context).getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, String.valueOf(sharedPref.getInt("current day calorie count", 0)) + " calories total to add!", Snackbar.LENGTH_SHORT);

                    snackbar.show();
                    rowView.setBackgroundColor(context.getResources().getColor(R.color.green));
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
