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
import android.view.Window;
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
        final int[] tabIcons = new int[]{
                R.drawable.fragment1icon,
                R.drawable.fragment1icon,
                R.drawable.fragment1icon
        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardFragment(), "Home");
        adapter.addFragment(new ThirdFragment(), "Fragment 2");
        adapter.addFragment(new SecondFragment(), "Fragment 3");
        viewPager.setAdapter(adapter);
    }

    public void onAddNewTaskClicked(View v) {
        View dialogView = View.inflate(this, R.layout.add_new_task_dialog_box, null);
        final EditText taskTime = (EditText) dialogView.findViewById(R.id.taskTimeField);
        final EditText descriptionBox = (EditText) dialogView.findViewById(R.id.taskDescriptionField);
        final EditText taskNameBox = (EditText) dialogView.findViewById(R.id.taskNameField);
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
