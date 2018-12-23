package com.example.app.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.models.StudentProfile;

import org.parceler.Parcels;

public class StudentProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private final static int REQUEST_EDIT_PROFILE = 101;

    private ImageView mEdit;
    private TextView mProfileName;
    private EditText mGender;
    private EditText mID;
    private EditText mSchoolId;
    private EditText mDateOfBirth;
    private EditText mGuardian;
    private EditText mClassName;
    private EditText mTelephone;
    private EditText mNationalID;
    private EditText mAverageGrade;
    private EditText mShoesize;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StudentProfile profile = Parcels.unwrap(getIntent().getParcelableExtra("StudentProfile"));

        mEdit = (ImageView) findViewById(R.id.edit);
        mEdit.setOnClickListener(this);

        mProfileName = (TextView) findViewById(R.id.name);
        mGender = (EditText) findViewById(R.id.student_gender);
        mID = (EditText) findViewById(R.id.student_id);
        mSchoolId = (EditText) findViewById(R.id.student_school_id);
        mDateOfBirth = (EditText) findViewById(R.id.student_birthdate);
        mGuardian = (EditText) findViewById(R.id.student_guardian);
        mClassName = (EditText) findViewById(R.id.student_class_name);
        mTelephone = (EditText) findViewById(R.id.student_phone);
        mNationalID = (EditText) findViewById(R.id.student_nationalID);
        mAverageGrade = (EditText) findViewById(R.id.student_grade);
        mShoesize = (EditText) findViewById(R.id.student_shoeSize);

        populateView(profile);
    }

    public void populateView(StudentProfile profile) {
        String fullname = profile.getFirstName() + " " + profile.getLastName();
        mProfileName.setText(fullname);
        mID.setText(profile.getId());
        mSchoolId.setText(profile.getStudentId());
        mDateOfBirth.setText(profile.getDOB());
        mGuardian.setText(profile.getGuardian());
        mGender.setText(profile.getGender());
        mClassName.setText(profile.getClassname());
        mTelephone.setText(profile.getTelephone());
        mNationalID.setText(profile.getNationalID());
        mAverageGrade.setText(profile.getAvegrade());
        mShoesize.setText(profile.getShoesize());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.edit){
            StudentProfile currentProfile = currentProfile();
            Intent intent = new Intent (this, EditStudentProfile.class);
            intent.putExtra("CurrentStudentProfile", Parcels.wrap(currentProfile));
            startActivityForResult(intent, REQUEST_EDIT_PROFILE);
        }
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == REQUEST_EDIT_PROFILE && resultcode == Activity.RESULT_OK) {
            StudentProfile result = Parcels.unwrap(data.getParcelableExtra("UpdatedStudentProfile"));
            String profilename = result.getFirstName() + " " + result.getLastName();
            if (profilename != (mProfileName.getText())) {
                mProfileName.setText(profilename);
            }
            if (!(mGender.getText().toString().equals(result.getGender()))) {
                mGender.setText(result.getGender());
            }
            if (!(mID.getText().toString().equals(result.getId()))) {
                mSchoolId.setText(result.getId());
            }
            if (!(mSchoolId.getText().toString().equals(result.getStudentId()))) {
                mSchoolId.setText(result.getId());
            }
            if (!(mDateOfBirth.getText().toString().equals(result.getDOB()))) {
                mDateOfBirth.setText(result.getDOB());
            }
            if (!(mGuardian.getText().toString().equals(result.getGuardian()))) {
                mGuardian.setText(result.getGuardian());
            }
            if (!(mClassName.getText().toString().equals(result.getClassname()))) {
                mClassName.setText(result.getClassname());
            }
            if (!(mTelephone.getText().toString().equals(result.getTelephone()))) {
                mTelephone.setText(result.getTelephone());
            }
            if (!(mNationalID.getText().toString().equals(result.getNationalID()))) {
                mNationalID.setText(result.getNationalID());
            }
            if (!(mAverageGrade.getText().toString().equals(result.getAvegrade()))) {
                mAverageGrade.setText(result.getAvegrade());
            }
            if (!(mShoesize.getText().toString().equals(result.getShoesize()))) {
                mShoesize.setText(result.getShoesize());
            }
        }
    }


    public StudentProfile currentProfile() {
        String[] fullname = mProfileName.getText().toString().split("\\s+");
        String firstname = fullname[0];
        String lastname = fullname[1];
        String gender = mGender.getText().toString();
        String id = mID.getText().toString();
        String schoolid = mSchoolId.getText().toString();
        String dateOfBirth = mDateOfBirth.getText().toString();
        String guardian = mGuardian.getText().toString();
        String classname = mClassName.getText().toString();
        String contact = mTelephone.getText().toString();
        String nationalid = mNationalID.getText().toString();
        String avggrade = mAverageGrade.getText().toString();
        String shoesize = mShoesize.getText().toString();
        return new StudentProfile(firstname, lastname, id , schoolid, gender, dateOfBirth, guardian,
                classname, contact, nationalid, avggrade, shoesize);
    }

}



