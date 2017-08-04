package com.androidapp.richard.startfresh.AdaptersAndOtherClasses;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidapp.richard.startfresh.R;

import java.util.ArrayList;

/**
 * Created by Richard Zhang on 2017-08-03.
 */

public class SpendingTrackerItemArrayAdapter extends ArrayAdapter<SpendingTrackerItem> {

    Context context;
    ArrayList<SpendingTrackerItem> itemList;
    public SpendingTrackerItemArrayAdapter(Context context, ArrayList<SpendingTrackerItem> itemList){
        super(context,-1);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.spending_tracker_item_row_view, parent, false);
        TextView itemName = (TextView) rowView.findViewById(R.id.item_name_text_view);
        TextView balancename = (TextView) rowView.findViewById(R.id.balance_change_text_view);
        balancename.setText(String.valueOf(itemList.get(position).getChangeInBalance()));
        itemName.setText(String.valueOf(itemList.get(position).getName()));
        return rowView;
    }
}
