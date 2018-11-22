package com.example.app.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Class;

import java.util.ArrayList;


public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassListViewHolder> {

    private  Activity mContext;
    private  ArrayList<Class> mClasses;
    private  OnItemClickListener mListener;


    public ClassListAdapter(Activity context, ArrayList<Class> classes_list, OnItemClickListener listener) {
        mClasses = classes_list;
        mContext = context;
        mListener = listener;
    }


    //populates the class_item view. i.e the view for each class in the dashboard
    //sets the name and size of the class in the view.
    @Override
    public void onBindViewHolder (ClassListViewHolder viewHolder, int position) {
        Class class_info = mClasses.get(position);
        viewHolder.mClassName.setText(class_info.getName());
        viewHolder.mClassSize.setText(class_info.getClassSize());

    }

    //creates a single row view in the recyclerview group for a single class
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




    public class ClassListViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public TextView mClassName;
        public TextView mClassSize;

        ClassListViewHolder(View itemView) {
            super(itemView);
            mClassName = (TextView) itemView.findViewById(R.id.class_name);
            mClassSize = (TextView) itemView.findViewById(R.id.class_size);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getAdapterPosition());
        }
    }
}
