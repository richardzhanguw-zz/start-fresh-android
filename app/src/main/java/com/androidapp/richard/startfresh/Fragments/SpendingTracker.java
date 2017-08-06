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
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class SpendingTracker extends Fragment {

    private OnFragmentInteractionListener mListener;
    Double amountLeft = 1500.0;
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
        final TextView amountLeftTextView = (TextView) view.findViewById(R.id.amount_left_text_view);
        final ListView listView = (ListView) view.findViewById(R.id.spending_tracker_list_view);
        FirebaseApp.initializeApp(getContext());
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final ArrayList<SpendingTrackerItem> listOfItems = new ArrayList<SpendingTrackerItem>();
        Date date = new Date();
        String currentDate = new SimpleDateFormat("MM-yyyy").format(date);
        dbRef.child("spending tracker list").child("list items").child(currentDate
        ).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    SpendingTrackerItem listItem = new SpendingTrackerItem(Double.parseDouble(child.child("price").getValue().toString()), child.child("name").getValue().toString(), child.getKey());
                    listOfItems.add(listItem);
                    amountLeft += listItem.getChangeInBalance();
                }
                final SpendingTrackerItemArrayAdapter adapter = new SpendingTrackerItemArrayAdapter(getContext(), listOfItems);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                amountLeftTextView.setText("$"+String.valueOf(amountLeft));
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
