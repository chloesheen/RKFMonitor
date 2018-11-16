package com.example.app.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassListViewHolder> implements OnItemClickListener{

    private  Activity mContext;
    private  ArrayList<Pair> mClasses;
    private OnItemClickListener mlistener;


    public ClassListAdapter(Activity context, ArrayList<Pair> classes_list) {
        mClasses = classes_list;
        mContext = context;
    }


    @Override
    public void onBindViewHolder (ClassListViewHolder viewHolder, int position) {
        Pair class_info = mClasses.get(position);

    }

    @Override
    public ClassListViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View row_of_class_view = LayoutInflater.from(group.getContext()).inflate(R.layout.class_item, group, false);
        ClassListViewHolder viewholder = new ClassListViewHolder(row_of_class_view);
        return viewholder;
    }


    @Override
    public int getItemCount() {
        return mClasses.size();
    }

    @Override
    public void onItemClick(int position) {

    }


    public class ClassListViewHolder extends RecyclerView.ViewHolder {

        public TextView mClassName;
        public TextView mClassSize;

        ClassListViewHolder(View itemView) {
            super(itemView);
            mClassName = (TextView) itemView.findViewById(R.id.class_name);
            mClassSize = (TextView) itemView.findViewById(R.id.class_size);
        }
    }
}
