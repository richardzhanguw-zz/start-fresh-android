package com.androidapp.richard.startfresh.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

import butterknife.ButterKnife;
import butterknife.OnClick;


public class SpendingTracker extends Fragment {

    private OnFragmentInteractionListener mListener;
    int amountLeft = 1500;
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
        ButterKnife.bind(this, view);
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
                    amountLeft += (int) listItem.getChangeInBalance().doubleValue();
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

    @OnClick(R.id.add_new_spending_item_button) void onAddNewSpendingItemClicked () {
        View dialogView = View.inflate(getContext(), R.layout.add_new_spending_item_dialog_box, null);
        final EditText itemNameBox = (EditText) dialogView.findViewById(R.id.item_name_edit_text);
        final EditText itemPricebox = (EditText) dialogView.findViewById(R.id.item_price_edit_text);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        FirebaseApp.initializeApp(getContext());
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                        Date date = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
                        String monthAndYear = dateFormat.format(date);
                        dbRef.child("spending tracker list").child("list items").child(monthAndYear).child(date.toString()).child("name").setValue(itemNameBox.getText().toString());
                        dbRef.child("spending tracker list").child("list items").child(monthAndYear).child(date.toString()).child("price").setValue(itemPricebox.getText().toString());
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };
        AlertDialog.Builder  adbuilder = new AlertDialog.Builder(getContext());
        adbuilder.setNegativeButton("Cancel", dialogClickListener)
                .setPositiveButton("Add Item", dialogClickListener)
                .setCancelable(true)
                .setTitle("Add a New Item")
                .setView(dialogView)
                .show();
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
