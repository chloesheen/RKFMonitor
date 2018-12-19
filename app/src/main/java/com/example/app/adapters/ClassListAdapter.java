package com.example.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.models.Student;
import com.example.app.util.Pair;

import java.util.ArrayList;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassListViewHolder> {

    private ArrayList<Pair> mClassList;
    private Context mContext;

    public ClassListAdapter(Context context, ArrayList<Pair> classes) {
        mClassList = classes;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassListViewHolder viewHolder,
                                 int position) {
        Pair classAttendance = mClassList.get(position);
        viewHolder.mClassName.setText(classAttendance.getFirst().toString());
        viewHolder.mAttendance.setText(classAttendance.getSecond().toString());
    }

    @NonNull
    @Override
    public ClassListViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View classview = LayoutInflater.from(group.getContext()).inflate(R.layout.class_item, group, false);
        return new ClassListViewHolder(classview);
    }

    @Override
    public int getItemCount() {
        return mClassList.size();
    }


    protected class ClassListViewHolder extends RecyclerView.ViewHolder {

        private TextView mClassName;
        private TextView mAttendance;

        public  ClassListViewHolder (View itemview) {
            super(itemview);
            mClassName = (TextView) itemview.findViewById(R.id.class_name);
            mAttendance = (TextView) itemview.findViewById(R.id.class_attendance);
        }
    }


}
