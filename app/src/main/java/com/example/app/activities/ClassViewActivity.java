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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.StudentListAdapter;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.asynctasks.HttpPutRequests;
import com.example.app.fragments.SuccessDialog;
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
import java.util.HashMap;

import static com.example.app.util.Constants.GET_STUDENTLIST_VIEW;
import static com.example.app.util.Constants.GET_STUDENT_PROFILE;
import static com.example.app.util.Constants.GET_TEACHER_PROFILE;
import static com.example.app.util.Constants.PUT_STUDENT_ATTENDANCE;
import static com.example.app.util.Constants.REQUEST_STUDENT_LIST;
import static com.example.app.util.Constants.REQUEST_STUDENT_PROFILE;
import static com.example.app.util.Constants.REQUEST_SUBMIT_ATTENDANCE;
import static com.example.app.util.Constants.REQUEST_TEACHER_PROFILE;

public class ClassViewActivity extends AppCompatActivity implements
        View.OnClickListener, CallbackListener {

    private ArrayList<Student> mStudents;
    private StudentListAdapter mStudentListAdapter;

    private HashMap<String, JSONArray> mAttendance = new HashMap<>();
    private ArrayList<String> mPresentList = new ArrayList<>();
    private ArrayList<String> mAbsentList = new ArrayList<>();

    private ClickListener mClickListener;

    private CallbackListener mListener;
    private Context mContext;

    private TextView mProfileName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mStudents = Parcels.unwrap(extras.getParcelable("Studentlist"));
            String schoolName = extras.getString("schoolname");
            String className = extras.getString("classname");
            String username = extras.getString("username");
            TextView mSchoolname = (TextView) findViewById(R.id.school_view);
            mSchoolname.setText(schoolName);
            TextView mClassname = (TextView) findViewById(R.id.class_name);
            mClassname.setText(className);
            mProfileName = (TextView) findViewById(R.id.profile_name);
            mProfileName.setText(username);
            mProfileName.setFocusable(true);
            mProfileName.setOnClickListener(this);
        }

        mListener = this;
        mContext = this;

        RecyclerView  mStudentView = (RecyclerView) findViewById(R.id.student_list_view);
        mStudentView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mStudentView.setLayoutManager(mLayoutManager);

        mStudentListAdapter = new StudentListAdapter(this, mStudents);
        mStudentView.setAdapter(mStudentListAdapter);

        Button mAddStudents = (Button) findViewById(R.id.add_student);
        mAddStudents.setOnClickListener(this);

        Button mSubmit = (Button) findViewById(R.id.submit_attendance);
        mSubmit.setOnClickListener(this);

        mClickListener = new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final CheckBox attendance = view.findViewById(R.id.attendance);
                attendance.setFocusable(true);
                final TextView studentname = view.findViewById(R.id.student_name);
                studentname.setFocusable(true);

                attendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v == attendance) {
                            attendance.requestFocus();
                            Student curStudent = mStudentListAdapter.getStudent(position);
                            curStudent.updateAttendance(attendance.isChecked());
                        } else if (v == studentname) {
                            studentname.requestFocus();
                            Student student = mStudentListAdapter.getStudent(position);
                            HttpGetRequests task = new HttpGetRequests(GET_STUDENT_PROFILE, mListener, mContext);
                            String studentid = student.getId();
                            try {
                                task.execute(REQUEST_STUDENT_PROFILE + "/" + studentid);
                            } catch(Exception e) {e.printStackTrace();}
                        }
                    };
                });
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
                mProfileName.requestFocus();
                HttpGetRequests gettask = new HttpGetRequests(GET_TEACHER_PROFILE, this, this);
                gettask.execute(REQUEST_TEACHER_PROFILE);
                break;

            case R.id.submit_attendance:
                getAttendanceStudents();
                JSONArray attending = new JSONArray(mPresentList);
                JSONArray notattending = new JSONArray(mAbsentList);
                mAttendance.put("attending", attending);
                mAttendance.put("notAttending", notattending);
                Log.v("attendance", mAttendance.toString());
                HttpPutRequests task = new HttpPutRequests(mAttendance, PUT_STUDENT_ATTENDANCE, this, this);
                task.execute(REQUEST_SUBMIT_ATTENDANCE);
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

    private void getAttendanceStudents() {
        for(Student stud:mStudents) {
            if (stud.isPresent()) {
                mPresentList.add(stud.getId());
            } else {
                mAbsentList.add(stud.getId());
            }
        }
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object) {
        if (success) {
            switch(requestcode) {
                case GET_STUDENT_PROFILE:
                    StudentProfile profile = (StudentProfile) object;
                    launchStudentProfile(profile);
                    break;

                case GET_TEACHER_PROFILE:
                    TeacherProfile teacherProfile = (TeacherProfile) object;
                    launchTeacherProfile(teacherProfile);
                    break;

                case PUT_STUDENT_ATTENDANCE:
                    mStudents = (ArrayList<Student>) object;
                    HttpGetRequests task = new HttpGetRequests(GET_STUDENTLIST_VIEW, mListener, mContext);
                    task.execute(REQUEST_STUDENT_LIST);
                    Fragment successIndicator = SuccessDialog.newInstance("Attendance Recorded!");
                    getSupportFragmentManager().beginTransaction()
                            .add(successIndicator, "SuccessFragment")
                            .commit();
                    break;
            }
        }
    }
}
