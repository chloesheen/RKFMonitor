package com.example.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.example.app.models.StudentProfile;

public class AddStudentActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

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
        String firstname = mFirstname.getText().toString();
        String lastname = mLastname.getText().toString();
        String dob = mDateOfBirth.getText().toString();
        String guardianname = mParentname.getText().toString();
        String contact = mParentContact.getText().toString();
        String natid = mParentID.getText().toString();
        String shoesize = mShoesize.getText().toString();
        String avegrade = "0.0";
        StudentProfile newProfile = new StudentProfile(firstname, lastname, null, null, mStudentGender,
                dob, guardianname, null, contact, natid, avegrade, shoesize);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        mStudentGender = parent.getItemAtPosition(pos).toString();
        Log.d("test1", mStudentGender);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
