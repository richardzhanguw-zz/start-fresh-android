package com.androidapp.richard.startfresh.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidapp.richard.startfresh.Activities.FoodTypeSelectionActivity;
import com.androidapp.richard.startfresh.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class HealthyCounterFragment extends Fragment implements  View.OnClickListener {

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
        dairy.setOnClickListener(this);
        fruitsandvegetables.setOnClickListener(this);
        grains.setOnClickListener(this);
        meat.setOnClickListener(this);
        other.setOnClickListener(this);
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.dairy_box:
                startActivity(new Intent(getActivity(), FoodTypeSelectionActivity.class));
                break;
            case R.id.fruits_and_vegetables_box:
                startActivity(new Intent(getActivity(), FoodTypeSelectionActivity.class));
                break;
            case R.id.grains_box:
                startActivity(new Intent(getActivity(), FoodTypeSelectionActivity.class));
                break;
            case R.id.meats_box:
                startActivity(new Intent(getActivity(), FoodTypeSelectionActivity.class));
                break;
            case R.id.other_foods_box:
                startActivity(new Intent(getActivity(), FoodTypeSelectionActivity.class));
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
