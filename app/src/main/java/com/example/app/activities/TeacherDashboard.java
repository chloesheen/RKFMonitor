package com.example.app.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.app.R;
import com.example.app.adapters.ClassListAdapter;
import com.example.app.interfaces.OnItemClickListener;

import java.util.ArrayList;

public class TeacherDashboard extends AppCompatActivity implements OnItemClickListener {

    ArrayList classes;
    RecyclerView classList;
    ClassListAdapter mClassListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        classList = (RecyclerView) findViewById(R.id.class_list_view);

        mClassListAdapter = new ClassListAdapter(this, classes);

        classList.setAdapter(mClassListAdapter);
    }



    @Override
    public void onItemClick(int position) {

    }

}
