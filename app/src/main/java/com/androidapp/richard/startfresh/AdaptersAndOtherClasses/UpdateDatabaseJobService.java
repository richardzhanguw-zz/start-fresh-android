package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhang on 2017-07-27.
 */
public class UpdateDatabaseJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
       FirebaseApp.initializeApp(this);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        String timeStamp = new SimpleDateFormat("MMM dd").format(Calendar.getInstance().getTime());
        SharedPreferences sharedPref = this.getSharedPreferences("caloriecount", Context.MODE_PRIVATE);
        dbRef.child("past data").child("calories").child(timeStamp).setValue(String.valueOf(sharedPref.getInt("current day calorie count", 0)));
        jobFinished(jobParameters, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

}
