package com.example.app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Student;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListViewHolder> {

    private ArrayList<Student> mStudents;
    private OnItemClickListener mListener;

    public StudentListAdapter(ArrayList<Student> student_list, OnItemClickListener listener) {
        mStudents = student_list;
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull  StudentListViewHolder viewHolder,
                                 int position) {

    }

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View student = LayoutInflater.from(group.getContext()).inflate(R.layout.student_item, group, false);
        return new StudentListViewHolder(student);
    }


    @Override
    public int getItemCount() {
        return 1;
    }


    protected class StudentListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mStudentName;
        private CheckBox mAttendance;
        private EditText mStudentNum;

        public  StudentListViewHolder (View itemview) {
            super(itemview);
            mStudentName = (TextView) itemview.findViewById(R.id.student_name);
            mAttendance = (CheckBox) itemview.findViewById(R.id.attendance);
            mStudentNum = (EditText) itemview.findViewById(R.id.student_number);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
