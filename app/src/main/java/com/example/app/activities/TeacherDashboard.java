package com.example.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.app.util.Pair;

import java.util.ArrayList;

public class TeacherDashboard extends AppCompatActivity implements OnItemClickListener {

    ArrayList mClasses;
    RecyclerView mClassList;
    ClassListAdapter mClassListAdapter;
    ArrayList mStudents;
    Context mContext;
    OnItemClickListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;

        mClassList = (RecyclerView) findViewById(R.id.class_list_view);

        mClassListAdapter = new ClassListAdapter(this, mClasses, mListener);

        mClassList.setAdapter(mClassListAdapter);
    }

    //currently only works for classes not for student profile
    @Override
    public void onItemClick(int position) {
        GetStudents task = new GetStudents();
        task.execute();

    }


    //Asynctask to retrieve list of students for database when a class is selected.s
    public class GetStudents extends AsyncTask<Void, Void, ArrayList<Pair>> {

        protected ArrayList<Pair> doInBackground(Void... params){
            return mClasses;
        }

        //starts the activity that displays the list f students in the class.
        protected void onPostExecute(ArrayList... params) {
            Intent intent = new Intent(mContext, ClassViewActivity.class);
            intent.putExtra("StudentInfo", mStudents);
            startActivity(intent);
        }
    }

}
