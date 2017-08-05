package com.androidapp.richard.startfresh.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.FoodType;
import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.FoodTypesArrayAdapter;
import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.SpendingTrackerItem;
import com.androidapp.richard.startfresh.AdaptersAndOtherClasses.SpendingTrackerItemArrayAdapter;
import com.androidapp.richard.startfresh.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SpendingTracker extends Fragment {

    private OnFragmentInteractionListener mListener;
    public SpendingTracker() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spendingtracker, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.spending_tracker_list_view);
        FirebaseApp.initializeApp(getContext());
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final ArrayList<SpendingTrackerItem> listOfItems = new ArrayList<SpendingTrackerItem>();
        dbRef.child("spending tracker list").child("list items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    SpendingTrackerItem listItem = new SpendingTrackerItem(Double.parseDouble(child.getValue().toString()), child.getKey());
                    listOfItems.add(listItem);
                }
                final SpendingTrackerItemArrayAdapter adapter = new SpendingTrackerItemArrayAdapter(getContext(), listOfItems);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final SpendingTrackerItemArrayAdapter adapter = new SpendingTrackerItemArrayAdapter(getContext(), listOfItems);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

    }
}
