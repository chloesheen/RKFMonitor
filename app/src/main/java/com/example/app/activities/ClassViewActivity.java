package com.example.app.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.app.R;
import com.example.app.adapters.StudentListAdapter;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Student;

import java.util.ArrayList;

public class ClassViewActivity extends AppCompatActivity implements OnItemClickListener {

    ArrayList<Student> mStudents;
    RecyclerView.LayoutManager mLayoutManager;
    StudentListAdapter mStudentListAdapter;
    RecyclerView mStudentView;
    OnItemClickListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStudentView = (RecyclerView) findViewById(R.id.student_list_view);

        mLayoutManager = new LinearLayoutManager(this);
        mStudentView.setLayoutManager(mLayoutManager);

        mStudentListAdapter = new StudentListAdapter(mStudents, mListener);
        mStudentView.setAdapter(mStudentListAdapter);

    }



    @Override
    public void onItemClick(int position) {

    }
}
