package com.androidapp.richard.startfresh.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.CustomXAxisLabelsFormatter;
import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.FoodType;
import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.FoodTypesArrayAdapter;
import com.androidapp.richard.startfresh.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConsumptionHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_history);
        final LineChart lineChart = (LineChart) findViewById(R.id.past_consumption_line_chart);
        final List<Entry> listOfEntries = new ArrayList<Entry>();
        final List<String> dates = new ArrayList<String>();
        FirebaseApp.initializeApp(this);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("past data").child("calories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("firebasestuff", String.valueOf(dataSnapshot.getChildrenCount()));
                int i = 0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    listOfEntries.add(new Entry(i, Float.parseFloat(child.getValue().toString())));
                    dates.add(child.getKey().toString());
                    i++;
                }
                LineDataSet dataSet = new LineDataSet(listOfEntries, null); // add entries to dataset
                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);
                lineChart.invalidate();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setValueFormatter(new CustomXAxisLabelsFormatter(dates));
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

    }
}
