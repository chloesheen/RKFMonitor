package com.example.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.interfaces.ClickListener;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Student;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListViewHolder> {

    private ArrayList<Student> mStudents;
    private Activity mContext;

    public StudentListAdapter(Context context,  ArrayList<Student> student_list) {
        mContext = (Activity) context;
        mStudents = student_list;
    }

    @Override
    public void onBindViewHolder(@NonNull  StudentListViewHolder viewHolder,
                                 int position) {
        Student student = mStudents.get(position);
        String studentname = student.getFirstName() + " " + student.getLastName();
        viewHolder.mStudentName.setText(studentname);
        viewHolder.mAttendance.setChecked(student.isPresent());
    }

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        View student = LayoutInflater.from(group.getContext()).inflate(R.layout.student_item, group, false);
        return new StudentListViewHolder(student);
    }

    public Student getStudent(int position) {
        //View positionview = rv.findChild
        return mStudents.get(position);
    }


    @Override
    public int getItemCount() {
        return mStudents.size();
    }


    protected class StudentListViewHolder extends RecyclerView.ViewHolder {

        private TextView mStudentName;
        private CheckBox mAttendance;

        public  StudentListViewHolder (View itemview) {
            super(itemview);
            mStudentName = (TextView) itemview.findViewById(R.id.student_name);
            mAttendance = (CheckBox) itemview.findViewById(R.id.attendance);
        }
    }
}
