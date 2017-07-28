package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by zhang on 2017-07-27.
 */
public class UpdateDatabaseJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
//        FirebaseApp.initializeApp(context);
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
//        dbRef.child("past data").child("calories").child("Jul 23").setValue("1540");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

}
