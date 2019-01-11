package com.example.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.app.util.ChildViewClickListener;
import com.example.app.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.parceler.Parcel;
import org.parceler.Parcels;
import org.w3c.dom.Text;

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
import static com.example.app.util.Constants.SHARED_PREFS_KEY;
import static com.example.app.util.DateUtils.setDate;

public class ClassViewActivity extends AppCompatActivity implements
        View.OnClickListener, CallbackListener{

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
            //String schoolName = extras.getString("schoolname");
            //String className = extras.getString("classname");
            //String username = extras.getString("username");
            //TextView mSchoolname = (TextView) findViewById(R.id.school_view);
            //mSchoolname.setText(schoolName);
            //TextView mClassname = (TextView) findViewById(R.id.class_name);
            //mClassname.setText(className);
            //mProfileName = (TextView) findViewById(R.id.teacherprofile);
            //mProfileName.setFocusable(true);
            //mProfileName.setOnClickListener(this);
            //mProfileName.setText(username);

        }

        TextView date = (TextView) findViewById(R.id.currentDate);
        date.setText(setDate());

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
                final CheckBox attendance = (CheckBox) view.findViewById(R.id.attendance);
                attendance.setFocusable(true);
                final TextView studentname = (TextView) view.findViewById(R.id.student_name);
                studentname.setFocusable(true);

                attendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v == attendance) {
                            attendance.requestFocus();
                            Log.v("attendance", String.valueOf(attendance.isChecked()));
                            Student curStudent = mStudentListAdapter.getStudent(position);
                            Log.v("name", curStudent.getFirstName());
                            Log.v("id", curStudent.getId());
                            Log.v("pastpresent", String.valueOf(curStudent.isPresent()));
                            if (attendance.isChecked()) {
                                curStudent.updateAttendance(true);
                            } else if (attendance.isChecked() == false) {
                                curStudent.updateAttendance(false);
                            }
                            Log.v("finalpresent", String.valueOf(curStudent.isPresent()));
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
        };

        mStudentView.addOnItemTouchListener(new ChildViewClickListener( this, mStudentView, mClickListener));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            HttpGetRequests gettask = new HttpGetRequests(GET_TEACHER_PROFILE, mListener, mContext);
            Log.v("profile", "testing teacher profile");
            gettask.execute(REQUEST_TEACHER_PROFILE);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_student:
                Intent intent = new Intent(this, AddStudentActivity.class);
                startActivity(intent);
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
        mPresentList = new ArrayList<>();
        mAbsentList = new ArrayList<>();
        for (int i = 0; i < mStudents.size(); i++) {
            Student stud = mStudentListAdapter.getStudent(i);
            Log.v("name", stud.getFirstName());
            Log.v("id", stud.getId());
            Log.v("present", String.valueOf(stud.isPresent()));
            if (stud.isPresent()) {
                mPresentList.add(stud.getId());
            } else if (!(stud.isPresent())) {
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
                    Fragment successIndicator = SuccessDialog.newInstance("Attendance Recorded!");
                    getSupportFragmentManager().beginTransaction()
                            .add(successIndicator, "SuccessFragment")
                            .commit();
                    HttpGetRequests task = new HttpGetRequests(GET_STUDENTLIST_VIEW, mListener, mContext);
                    task.execute(REQUEST_STUDENT_LIST);
                    break;

                case GET_STUDENTLIST_VIEW:
                    mStudents = (ArrayList<Student>) object;
                    for (Student stud : mStudents) {
                        Log.v("student", stud.toString());
                    }
                    break;

            }
        }
    }
}
