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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HealthyCounterFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.calorie_counter_text) TextView calorieCounterText;
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
        unbinder = ButterKnife.bind(this, view);
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

    @OnClick(R.id.see_past_counts_button) void onPastCountsButtonClick() {
        startActivity(new Intent(getActivity(), ConsumptionHistoryActivity.class));
    }

    @OnClick(R.id.dairy_box) void onDairyCategoryClicked() {
        startActivityWithExtra("milk and alternatives");
    }

    @OnClick(R.id.fruits_and_vegetables_box) void onFruitsAndVegetablesCategoryClicked() {
        startActivityWithExtra("fruits and vegetables");
    }

    @OnClick(R.id.grains_box) void onGrainCategoryClicked() {
        startActivityWithExtra("grain products");
    }

    @OnClick(R.id.meats_box) void onMeatCategoryClicked() {
        startActivityWithExtra("meat and alternatives");
    }

    @OnClick(R.id.other_foods_box) void onOtherFoodsCategory() {
        startActivityWithExtra("other foods");
    }

    void startActivityWithExtra(String extra) {
        Intent i = new Intent(getActivity(), FoodTypeSelectionActivity.class);
        i.putExtra("food group selected", extra);
        startActivity(i);
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
        unbinder.unbind();
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
