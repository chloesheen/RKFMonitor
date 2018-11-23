package com.example.app.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.StudentListAdapter;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Student;
import com.example.app.models.StudentProfile;

import java.util.ArrayList;

public class ClassViewActivity extends AppCompatActivity implements OnItemClickListener,
        View.OnClickListener{

    ArrayList<Student> mStudents;
    RecyclerView.LayoutManager mLayoutManager;
    StudentListAdapter mStudentListAdapter;
    RecyclerView mStudentView;
    OnItemClickListener mListener;

    Button mAddStudents;
    TextView mProfileName;


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

        mAddStudents = (Button) findViewById(R.id.add_student);
        mAddStudents.setOnClickListener(this);

        mProfileName = (TextView) findViewById(R.id.profile_name);
        mProfileName.setOnClickListener(this);
    }



    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_student:
                Intent intent = new Intent(this, AddStudentActivity.class);
                startActivity(intent);
                break;

            case R.id.profile_name:
                Intent profileIntent = new Intent(this, TeacherProfileActivity.class);
                startActivity(profileIntent);
        }
    }

    private class GetStudentProfile extends AsyncTask<Integer, Void, StudentProfile> {


        @Override
        protected StudentProfile doInBackground(Integer... params) {
            StudentProfile s = new StudentProfile("Coco", "Chanel", "24", true);
            return s;
        }

        @Override
        protected void onPostExecute(StudentProfile student) {

        }

    }
}
