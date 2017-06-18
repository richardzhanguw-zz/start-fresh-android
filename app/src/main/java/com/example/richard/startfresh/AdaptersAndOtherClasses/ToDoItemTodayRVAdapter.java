package com.example.richard.startfresh.AdaptersAndOtherClasses;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.richard.startfresh.R;

import java.util.List;

/**
 * Created by Richard on 2016-02-27.
 */
public class ToDoItemTodayRVAdapter extends RecyclerView.Adapter<ToDoItemTodayRVAdapter.ToDoItemTodayViewHolder> {

    List<ToDoItemToday> listOfItems;

    public ToDoItemTodayRVAdapter(List<ToDoItemToday> listOfItems){
        this.listOfItems = listOfItems;
    }
    @Override
    public ToDoItemTodayRVAdapter.ToDoItemTodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item_today_card, parent, false);
        ToDoItemTodayViewHolder item = new ToDoItemTodayViewHolder(v);
        return item;    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public void onBindViewHolder(final ToDoItemTodayRVAdapter.ToDoItemTodayViewHolder holder, int position) {
        //holder.taskName.setText(listOfItems.get(position).taskName);
        holder.taskExpanded.setText(listOfItems.get(position).taskExpanded);
        holder.taskDetails.setText(listOfItems.get(position).taskDetails);
        holder.timeOfTask.setText(listOfItems.get(position).timeOfTask);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.additionalInfo.getVisibility() == View.GONE) {
                    holder.additionalInfo.setVisibility(View.VISIBLE);
                }else{
                    holder.additionalInfo.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }
    public static class ToDoItemTodayViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView taskExpanded;
        TextView taskDetails;
        TextView timeOfTask;
        RelativeLayout additionalInfo;
        public ToDoItemTodayViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            taskExpanded = (TextView)itemView.findViewById(R.id.task_expanded);
            taskDetails = (TextView)itemView.findViewById(R.id.task_details);
            timeOfTask = (TextView)itemView.findViewById(R.id.time_of_task);
            additionalInfo = (RelativeLayout)itemView.findViewById(R.id.additional_info);
        }

    }
}
