package com.example.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.app.R;
import com.example.app.asynctasks.HttpPostRequests;
import com.example.app.fragments.SuccessDialog;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.StudentProfile;

import java.util.HashMap;

import static com.example.app.util.Constants.POST_NEW_STUDENT;

public class AddStudentActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, CallbackListener {

    private EditText mFirstname;
    private EditText mLastname;
    private EditText mDateOfBirth;
    private EditText mParentname;
    private EditText mParentContact;
    private EditText mParentID;
    private EditText mShoesize;

    private String mStudentGender;

    //Infor we need from shared preferences, teacher log in name, class name,
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirstname = (EditText) findViewById(R.id.student_firstname);
        mLastname = (EditText) findViewById(R.id.student_lastname);
        mDateOfBirth = (EditText) findViewById(R.id.student_birthdate);
        mParentname = (EditText) findViewById(R.id.guardian_name);
        mParentContact = (EditText) findViewById(R.id.student_guardianContact);
        mParentID = (EditText)findViewById(R.id.student_guardianID);
        mShoesize = (EditText) findViewById(R.id.student_shoesize);

        Spinner mGender = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGender.setAdapter(adapter);
        mGender.setOnItemSelectedListener(this);

        Button mAddStudent = (Button) findViewById(R.id.add_new_student);
        mAddStudent.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){
        HashMap<String, String> studentProfile = new HashMap<>();
        studentProfile.put("firstname", mFirstname.getText().toString());
        studentProfile.put("lastname", mLastname.getText().toString());
        studentProfile.put("gender", mStudentGender);
        studentProfile.put("dob", mDateOfBirth.getText().toString());
        studentProfile.put("classname", "");
        studentProfile.put("guardian", mParentname.getText().toString());
        studentProfile.put("contact", mParentContact.getText().toString());
        studentProfile.put("nationalid", mParentID.getText().toString());
        studentProfile.put("avegrade", "0.0");
        studentProfile.put("shoesize", mShoesize.getText().toString());
        HttpPostRequests task = new HttpPostRequests(studentProfile, POST_NEW_STUDENT, this);
        task.execute("");

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        mStudentGender = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object) {
        if (success & requestcode == POST_NEW_STUDENT) {
            Fragment successIndicator = SuccessDialog.newInstance("Student Successfully Added");
            getSupportFragmentManager().beginTransaction()
                    .add(successIndicator, "SuccessFragment")
                    .commit();
        }
    }
}
