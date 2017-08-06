package com.androidapp.richard.startfresh.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.FoodType;
import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.FoodTypesArrayAdapter;
import com.androidapp.richard.startfresh.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodTypeSelectionActivity extends Activity {
    ArrayList<FoodType> originalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_food_type_selection);
        originalList = new ArrayList<FoodType>();
        final ListView foodSelectionList = (ListView) findViewById(R.id.food_selection_list_view);
        EditText searchTextField = (EditText) findViewById(R.id.search_foods_edit_text);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.food_selection_coordinatorayout);
        final ArrayList<FoodType> listOfFoods = new ArrayList<FoodType>();
        FirebaseApp.initializeApp(this);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        if (getIntent().getExtras() != null) {
            String foodGroupSelected = getIntent().getExtras().getString("food group selected");
            dbRef.child("food groups").child(foodGroupSelected).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            FoodType food = new FoodType(child.child("name").getValue().toString(), child.child("calories").getValue().toString(), child.child("per_amount").getValue().toString());
                            listOfFoods.add(food);
                            originalList.add(food);
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            final FoodTypesArrayAdapter adapter = new FoodTypesArrayAdapter(this, listOfFoods,coordinatorLayout );
            foodSelectionList.setAdapter(adapter);
            foodSelectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    RelativeLayout expandableView = (RelativeLayout)view.findViewWithTag("expandableview"+String.valueOf(i));
                    expandableView.setVisibility(View.VISIBLE);
                }
            });
            searchTextField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String currentText = editable.toString();
                    listOfFoods.clear();
                    for (int j = 0; j< originalList.size(); j ++){
                        if(currentText.equals(originalList.get(j).getFoodName().substring(0, editable.length()))){
                            listOfFoods.add(originalList.get(j));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }


}
