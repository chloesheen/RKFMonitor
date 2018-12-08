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
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.asynctasks.SubmitAttendance;
import com.example.app.interfaces.CallbackListener;
import com.example.app.interfaces.ClickListener;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Student;
import com.example.app.models.StudentProfile;
import com.example.app.models.TeacherProfile;
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

import static com.example.app.util.Constants.GET_STUDENT_PROFILE;
import static com.example.app.util.Constants.GET_TEACHER_PROFILE;
import static com.example.app.util.Constants.REQUEST_STUDENT_PROFILE;
import static com.example.app.util.Constants.REQUEST_SUBMIT_ATTENDANCE;
import static com.example.app.util.Constants.REQUEST_TEACHER_PROFILE;

public class ClassViewActivity extends AppCompatActivity implements
        View.OnClickListener, CallbackListener {

    private ArrayList<Student> mStudents;
    private StudentListAdapter mStudentListAdapter;

    private Button mAddStudents;
    private TextView mProfileName;
    private Button mSubmit;

    private ArrayList<Pair> mAttendance;
    private Pair<String, ArrayList<String>> mPresentStudents;
    private Pair<String, ArrayList<String>> mAbsentStudents;

    private SharedPreferences mSharedPreferences;
    private ClickListener mClickListener;

    private CallbackListener mListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStudents = Parcels.unwrap(getIntent().getParcelableExtra(""));

        mListener = this;

        ArrayList<String> mPresentList = new ArrayList<>();
        ArrayList<String> mAbsentList = new ArrayList<>();
        mPresentStudents = new Pair("attending", mPresentList);
        mAbsentStudents = new Pair("notAttending", mAbsentList);

        RecyclerView  mStudentView = (RecyclerView) findViewById(R.id.student_list_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
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
                        HttpGetRequests task = new HttpGetRequests(GET_STUDENT_PROFILE, mListener);
                        String studentid = student.getId();
                        try {
                            task.execute(REQUEST_STUDENT_PROFILE + "/" + studentid);
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
                HttpGetRequests gettask = new HttpGetRequests(GET_TEACHER_PROFILE, this);
                gettask.execute(REQUEST_TEACHER_PROFILE);
                break;

            case R.id.submit_attendance:
                mAttendance.add(mPresentStudents);
                mAttendance.add(mAbsentStudents);
                SubmitAttendance task = new SubmitAttendance();
                task.execute(REQUEST_SUBMIT_ATTENDANCE, mAttendance.toString());
        }
    }

    /**
     * Launches the different profile activites based on what views are clicked
     * @param profile: the profile of the selected student and teacher
     */

    private void launchStudentProfile(StudentProfile profile) {
        Intent intent = new Intent(this, StudentProfileActivity.class);
        intent.putExtra("StudentProfile", Parcels.wrap(profile));
        startActivity(intent);
    }

    private void launchTeacherProfile(TeacherProfile profile) {
        Intent intent = new Intent(this, TeacherProfileActivity.class);
        intent.putExtra("TeacherProfile", Parcels.wrap(profile));
        startActivity(intent);
    }

    private void getAbsentStudents() {
        for(Student stud:mStudents) {
            if(!(mPresentStudents.getSecond().contains(stud.getId()))) {
                mAbsentStudents.getSecond().add(stud.getId());
            }
        }
    }

    @Override
    public void onCompletionHandler(Boolean success, int requestcode, Object object) {
        if (success && requestcode == GET_STUDENT_PROFILE){
            StudentProfile profile = (StudentProfile) object;
            launchStudentProfile(profile);
        } else if (success && requestcode == GET_TEACHER_PROFILE) {
            TeacherProfile teacherProfile = (TeacherProfile) object;
            launchTeacherProfile(teacherProfile);
        }
    }

}
