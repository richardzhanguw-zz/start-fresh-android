package com.androidapp.richard.startfresh.Fragments;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidapp.richard.startfresh.Activities.ConsumptionHistoryActivity;
import com.androidapp.richard.startfresh.Activities.FoodTypeSelectionActivity;
import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.UpdateDatabaseJobService;
import com.androidapp.richard.startfresh.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HealthyCounterFragment extends Fragment implements  View.OnClickListener {

    TextView calorieCounterText;
    public HealthyCounterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.healthycounter, container, false);
        TextView dairy = (TextView) view.findViewById(R.id.dairy_box);
        TextView fruitsandvegetables = (TextView) view.findViewById(R.id.fruits_and_vegetables_box);
        TextView grains = (TextView) view.findViewById(R.id.grains_box);
        TextView meat = (TextView) view.findViewById(R.id.meats_box);
        TextView other = (TextView) view.findViewById(R.id.other_foods_box);
        Button seePastCountsButton = (Button) view.findViewById(R.id.see_past_counts_button);
        seePastCountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ConsumptionHistoryActivity.class));
            }
        });
        dairy.setOnClickListener(this);
        fruitsandvegetables.setOnClickListener(this);
        grains.setOnClickListener(this);
        meat.setOnClickListener(this);
        other.setOnClickListener(this);
        calorieCounterText = (TextView) view.findViewById(R.id.calorie_counter_text);
        ComponentName componentName = new ComponentName(getContext(), UpdateDatabaseJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresDeviceIdle(true)
                .setPeriodic(86400000)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("caloriecount", Context.MODE_PRIVATE);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        if(sharedPref.getString("most recently added entry date", "").equals(dateFormat.format(date))){
            calorieCounterText.setText(String.valueOf(sharedPref.getInt("current day calorie count", 0)));
        } else {
            calorieCounterText.setText("0");
        }
    }
    @Override
    public void onClick(View view) {
        String foodGroupSelected = "";
        switch(view.getId()) {
            case R.id.dairy_box:
                foodGroupSelected = "milk and alternatives";
                break;
            case R.id.fruits_and_vegetables_box:
                foodGroupSelected = "fruits and vegetables";
                break;
            case R.id.grains_box:
                foodGroupSelected = "grain products";
                break;
            case R.id.meats_box:
                foodGroupSelected = "meat and alternatives";
                break;
            case R.id.other_foods_box:
                foodGroupSelected = "other foods";
                break;
            default:
                break;
        }
        Intent i = new Intent(getActivity(), FoodTypeSelectionActivity.class);
        i.putExtra("food group selected", foodGroupSelected);
        startActivity(i);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
