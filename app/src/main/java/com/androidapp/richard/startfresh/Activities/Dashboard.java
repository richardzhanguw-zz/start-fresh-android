package com.androidapp.richard.startfresh.Activities;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.ToDoItemToday;
import com.androidapp.richard.startfresh.Fragments.DashboardFragment;
import com.androidapp.richard.startfresh.Fragments.HealthyCounterFragment;
import com.androidapp.richard.startfresh.Fragments.SpendingTracker;
import com.androidapp.richard.startfresh.Manifest;
import com.androidapp.richard.startfresh.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Dashboard extends AppCompatActivity implements DashboardFragment.OnFragmentInteractionListener, HealthyCounterFragment.OnFragmentInteractionListener, SpendingTracker.OnFragmentInteractionListener {
    ViewPager viewPager;
    TabLayout tabLayout;
    DashboardFragment dashFrag;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        //set up tab layout
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        final int[] tabIcons = new int[]{
                R.drawable.fragment1icon,
                R.drawable.money_symbol,
                R.drawable.diet_tracker_symbol
        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardFragment(), "Home");
        adapter.addFragment(new SpendingTracker(), "Money Left");
        adapter.addFragment(new HealthyCounterFragment(), "Diet Tracker");
        viewPager.setAdapter(adapter);
    }

    public void onAddNewTaskClicked(View v) {
        View dialogView = View.inflate(this, R.layout.add_new_task_dialog_box, null);
        final TimePicker taskTime = (TimePicker) dialogView.findViewById(R.id.taskTimeField);
        taskTime.setIs24HourView(false);
        final EditText descriptionBox = (EditText) dialogView.findViewById(R.id.taskDescriptionField);
        final EditText taskNameBox = (EditText) dialogView.findViewById(R.id.taskNameField);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dashFrag = (DashboardFragment) adapter.getItem(0);
                        String amOrPm = (taskTime.getCurrentHour()<12) ? "am" : "pm";
                        int currentHour = taskTime.getCurrentHour();
                        String currentHourText;
                        if(amOrPm.equals("am") && currentHour == 0){
                            currentHourText = "12";
                        } else if (currentHour < 10){
                            currentHourText = "0" + String.valueOf(currentHour);
                        } else {
                            currentHourText = String.valueOf(currentHour);
                        }
                        String currentMinuteText = (taskTime.getCurrentMinute()<10) ? "0" + String.valueOf(taskTime.getCurrentMinute()): String.valueOf(taskTime.getCurrentMinute());
                        String currentTime = currentHourText+":"+currentMinuteText +" " + amOrPm;
                        ToDoItemToday newTask = new ToDoItemToday("Task for Tomorrow", taskNameBox.getText().toString(),"Tomorrow at " + currentTime, descriptionBox.getText().toString());
                        dashFrag.getListOfItems().add(newTask);
                        dashFrag.getRvAdapter().notifyDataSetChanged();
                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        Calendar calendar = Calendar.getInstance();
                        //Date today = calendar.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        Date tomorrow = calendar.getTime();
                        String tomorrowAsString = dateFormat.format(tomorrow);
                        //String todayAsString = dateFormat.format(today);
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        editor.putInt(tomorrowAsString, sharedPref.getInt(tomorrowAsString, 0) + 1);
                        int currentTaskNumberAdded = sharedPref.getInt(tomorrowAsString, 0);
                        editor.putString("task"+String.valueOf(currentTaskNumberAdded)+"-name", taskNameBox.getText().toString());
                        editor.putString("task"+String.valueOf(currentTaskNumberAdded)+"-details", descriptionBox.getText().toString());
                        editor.putString("task"+String.valueOf(currentTaskNumberAdded)+"-time", currentTime);
                        editor.apply();
                        Snackbar.make(findViewById(R.id.base_layout_main), "Task Added For Tomorrow!", Snackbar.LENGTH_LONG).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder  adbuilder = new AlertDialog.Builder(this);
        adbuilder.setNegativeButton("Cancel", dialogClickListener)
                .setPositiveButton("Add Task", dialogClickListener)
                .setCancelable(true)
                .setTitle("Add a New Task For Tomorrow")
                .setView(dialogView)
                .show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("MainActivity", "dashboard OAR hit");
        // Check if result comes from the correct activity

    }
}
