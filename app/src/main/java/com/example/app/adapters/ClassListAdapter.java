package com.example.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.models.Class;

import java.util.ArrayList;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassListViewHolder> {

    private ArrayList<Class> mClassList;
    private Context mContext;

    public ClassListAdapter(Context context, ArrayList<Class> classes) {
        mClassList = classes;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassListViewHolder viewHolder,
                                 int position) {
        Class thisclass = mClassList.get(position);
        viewHolder.mClassName.setText(thisclass.getName());
    }

    @NonNull
    @Override
    public ClassListViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View classview = LayoutInflater.from(group.getContext()).inflate(R.layout.single_view_item, group, false);
        return new ClassListViewHolder(classview);
    }

    @Override
    public int getItemCount() {
        return mClassList.size();
    }

    public Class getSelectedClass(int position) {
        return mClassList.get(position);
    }


    protected class ClassListViewHolder extends RecyclerView.ViewHolder {

        private TextView mClassName;

        public  ClassListViewHolder (View itemview) {
            super(itemview);
            mClassName = (TextView) itemview.findViewById(R.id.class_name);
        }
    }


}
