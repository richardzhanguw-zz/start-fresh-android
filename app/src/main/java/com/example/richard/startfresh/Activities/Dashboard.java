package com.example.richard.startfresh.Activities;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.richard.startfresh.AdaptersAndOtherClasses.ToDoItemToday;
import com.example.richard.startfresh.Fragments.DashboardFragment;
import com.example.richard.startfresh.Fragments.SecondFragment;
import com.example.richard.startfresh.Fragments.ThirdFragment;
import com.example.richard.startfresh.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Dashboard extends AppCompatActivity implements DashboardFragment.OnFragmentInteractionListener, SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener {
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
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardFragment(), "Home");
        adapter.addFragment(new ThirdFragment(), "Fragment 2");
        adapter.addFragment(new SecondFragment(), "Fragment 3");
        viewPager.setAdapter(adapter);
    }

    public void onAddNewTaskClicked(View v) {
        final EditText taskTime = new EditText(this);
        final EditText descriptionBox = new EditText(this);
        final EditText taskNameBox = new EditText(this);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dashFrag = (DashboardFragment) adapter.getItem(0);
                        dashFrag.getListOfItems().add(new ToDoItemToday("Task #1", taskNameBox.getText().toString(),"Tomorrow at " + taskTime.getText().toString(), taskNameBox.getText().toString()));
                        dashFrag.getRvAdapter().notifyDataSetChanged();
                        Snackbar.make(findViewById(R.id.base_layout_main), "Task Added For Tomorrow!", Snackbar.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog ad = new AlertDialog.Builder(this)
                .setNegativeButton("Cancel", dialogClickListener)
                .setPositiveButton("Add Task", dialogClickListener)
                .create();

        ad.setCancelable(true);
        ad.setTitle("Add a New Task For Tomorrow");
        ad.setMessage("Add a new task for tomorrow here.");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        taskNameBox.setHint("Task Name");
        layout.addView(taskNameBox);
        descriptionBox.setHint("Task Description");
        layout.addView(descriptionBox);
        taskTime.setHint("Time of Task");
        taskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Dashboard.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        taskTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        layout.addView(taskTime);
        ad.setView(layout);
        ad.show();
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
