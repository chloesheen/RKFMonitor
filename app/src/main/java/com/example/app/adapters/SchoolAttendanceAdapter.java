package com.example.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.util.Pair;

import java.util.ArrayList;

public class SchoolAttendanceAdapter extends RecyclerView.Adapter<SchoolAttendanceAdapter.AttendanceViewHolder> {

    private ArrayList<Pair> mAttendanceList;
    private Context mContext;

    public SchoolAttendanceAdapter(Context context, ArrayList<Pair> classes) {
        mAttendanceList = classes;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder viewHolder,
                                 int position) {
        Pair classAttendance =  mAttendanceList.get(position);
        viewHolder.mDate.setText(classAttendance.getFirst().toString());
        viewHolder.mAttendance.setText(classAttendance.getSecond().toString());
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View classview = LayoutInflater.from(group.getContext()).inflate(R.layout.daily_attendance_item, group, false);
        return new AttendanceViewHolder(classview);
    }

    @Override
    public int getItemCount() {
        return  mAttendanceList.size();
    }


    protected class AttendanceViewHolder extends RecyclerView.ViewHolder {

        private TextView mDate;
        private TextView mAttendance;

        public  AttendanceViewHolder (View itemview) {
            super(itemview);
            mDate = (TextView) itemview.findViewById(R.id.date);
            mAttendance = (TextView) itemview.findViewById(R.id.class_attendance);
        }
    }
}
