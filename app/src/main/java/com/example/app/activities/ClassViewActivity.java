package com.example.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.StudentListAdapter;
import com.example.app.asynctasks.GetStudentProfile;
import com.example.app.asynctasks.SubmitAttendance;
import com.example.app.interfaces.ClickListener;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Student;
import com.example.app.models.StudentProfile;
import com.example.app.util.Pair;
import com.example.app.util.StudentListClickListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.app.util.Constants.REQUEST_STUDENT_PROFILE;
import static com.example.app.util.Constants.REQUEST_SUBMIT_ATTENDANCE;

public class ClassViewActivity extends AppCompatActivity implements
        View.OnClickListener {

    private ArrayList<Student> mStudents;
    private RecyclerView.LayoutManager mLayoutManager;
    private StudentListAdapter mStudentListAdapter;
    private RecyclerView mStudentView;

    private Button mAddStudents;
    private TextView mProfileName;
    private Button mSubmit;

    private ArrayList<Pair> mAttendance;
    private Pair<String, ArrayList<String>> mPresentStudents;
    private Pair<String, ArrayList<String>> mAbsentStudents;

    private SharedPreferences mSharedPreferences;
    private ClickListener mClickListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStudents = Parcels.unwrap(getIntent().getParcelableExtra(""));

        ArrayList<String> mPresentList = new ArrayList<>();
        ArrayList<String> mAbsentList = new ArrayList<>();
        mPresentStudents = new Pair("attending", mPresentList);
        mAbsentStudents = new Pair("notAttending", mAbsentList);

        mStudentView = (RecyclerView) findViewById(R.id.student_list_view);

        mLayoutManager = new LinearLayoutManager(this);
        mStudentView.setLayoutManager(mLayoutManager);

        mStudentListAdapter = new StudentListAdapter(this, mStudents);
        mStudentView.setAdapter(mStudentListAdapter);

        mAddStudents = (Button) findViewById(R.id.add_student);
        mAddStudents.setOnClickListener(this);

        mProfileName = (TextView) findViewById(R.id.profile_name);
        mProfileName.setOnClickListener(this);

        mSubmit = (Button) findViewById(R.id.submit_attendance);
        mSubmit.setOnClickListener(this);

        mClickListener = new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                int childviewid = view.getId();
                switch (childviewid) {
                    case R.id.attendance:
                        Student curStudent = mStudentListAdapter.getStudent(position);
                        mPresentStudents.getSecond().add(curStudent.getId());
                        break;

                    case R.id.student_name:
                        Student student = mStudentListAdapter.getStudent(position);
                        GetStudentProfile task = new GetStudentProfile();
                        String studentid = student.getId();
                        try {
                            StudentProfile profile = task.execute(REQUEST_STUDENT_PROFILE + "/" + studentid).get();
                            launchStudentProfile(profile);
                        } catch(Exception e) {e.printStackTrace();}
                        break;
                }
            }

            @Override
            public void onLongPress(View view, int position) {

            }
        };

        mStudentView.addOnItemTouchListener(new StudentListClickListener( this, mStudentView, mClickListener));
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
                break;

            case R.id.submit_attendance:
                mAttendance.add(mPresentStudents);
                mAttendance.add(mAbsentStudents);
                SubmitAttendance task = new SubmitAttendance();
                task.execute(REQUEST_SUBMIT_ATTENDANCE, mAttendance.toString());
        }
    }

    /**
     * Launches the student profile activity
     * @param profile: the profile of the selected student
     */

    private void launchStudentProfile(StudentProfile profile) {
        Intent intent = new Intent(this, StudentProfileActivity.class);
        intent.putExtra("StudentProfile", Parcels.wrap(profile));
        startActivity(intent);
    }

    private void getAbsentStudents() {
        for(Student stud:mStudents) {
            if(!(mPresentStudents.getSecond().contains(stud.getId()))) {
                mAbsentStudents.getSecond().add(stud.getId());
            }
        }
    }

}
