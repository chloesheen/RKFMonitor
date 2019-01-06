package com.example.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.models.Calendar;

import java.util.ArrayList;
import java.util.Date;

public class SchoolFeedingAdapter extends RecyclerView.Adapter<SchoolFeedingAdapter.FeedingViewHolder>  {

    private ArrayList<Calendar> mFeedingList;
    private Context mContext;

    public SchoolFeedingAdapter(Context context, ArrayList<Calendar> feedingList) {
        mFeedingList = feedingList;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedingViewHolder viewHolder,
                                 int position) {
        com.example.app.models.Calendar classAttendance =  mFeedingList.get(position);
        viewHolder.mDate.setText(classAttendance.getFirst().toString());
    }

    @NonNull
    @Override
    public FeedingViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View classview = LayoutInflater.from(group.getContext()).inflate(R.layout.single_view_item, group, false);
        return new SchoolFeedingAdapter.FeedingViewHolder(classview);
    }

    @Override
    public int getItemCount() {
        return  mFeedingList.size();
    }


    public Calendar getSelectedDate(int position) {
        return mFeedingList.get(position);
    }


    protected class FeedingViewHolder extends RecyclerView.ViewHolder {

        private TextView mDate;

        public  FeedingViewHolder (View itemview) {
            super(itemview);
            mDate = (TextView) itemview.findViewById(R.id.class_name);
        }
    }
}
