package com.androidapp.richard.startfresh.Activities;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.androidapp.richard.startfresh.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodTypeSelectionActivity extends Activity {
    ArrayList<String> originalList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.activity_food_type_selection);
        originalList = new ArrayList<String>();
        final ListView foodSelectionList = (ListView) findViewById(R.id.food_selection_list_view);
        EditText searchTextField = (EditText) findViewById(R.id.search_foods_edit_text);
        final ArrayList<String> listOfFoods = new ArrayList<String>();
        FirebaseApp.initializeApp(this);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        if (getIntent().getExtras() != null) {
            String foodGroupSelected = getIntent().getExtras().getString("food group selected");
            dbRef.child("food groups").child(foodGroupSelected).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot == null) {
                        Log.d("ondatachange", "is null");
                    } else {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            listOfFoods.add(child.getValue().toString());
                            originalList.add(child.getValue().toString());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            final ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, listOfFoods);
            foodSelectionList.setAdapter(adapter);
            foodSelectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
                        if(currentText.equals(originalList.get(j).substring(0, editable.length()))){
                            listOfFoods.add(originalList.get(j));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }


}
