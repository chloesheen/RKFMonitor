package com.example.app.activities;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.R;
import com.example.app.asynctasks.HttpPutRequests;
import com.example.app.asynctasks.HttpDeleteRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.StudentProfile;

import java.util.HashMap;
import static com.example.app.util.Constants.PUT_STUDENT_PROFILE;
import static com.example.app.util.Constants.DELETE_STUDENT_PROFILE;
import org.parceler.Parcels;

public class EditStudentProfile extends AppCompatActivity implements View.OnClickListener, CallbackListener{

    private EditText mFirstName;
    private EditText mLastName;
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
    private Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirstName = (EditText) findViewById(R.id.edit_student_firstname);
        mLastName = (EditText) findViewById(R.id.edit_student_lastname);
        mGender = (EditText) findViewById(R.id.edit_student_gender);
        mID = (EditText) findViewById(R.id.edit_student_id);
        mSchoolId = (EditText) findViewById(R.id.edit_student_school_id);
        mDateOfBirth = (EditText) findViewById(R.id.edit_student_birthdate);
        mGuardian = (EditText) findViewById(R.id.edit_student_guardian);
        mClassName = (EditText) findViewById(R.id.edit_student_class_name);
        mTelephone = (EditText) findViewById(R.id.edit_student_phone);
        mNationalID = (EditText) findViewById(R.id.edit_student_nationalID);
        mAverageGrade = (EditText) findViewById(R.id.edit_student_grade);
        mShoesize = (EditText) findViewById(R.id.edit_student_shoeSize);

        StudentProfile input = Parcels.unwrap(getIntent().
                getParcelableExtra("CurrentStudentProfile"));
        setProfile(input);

        mSave = (Button) findViewById(R.id.save);
        mSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save) {
            StudentProfile editedprofile = updateProfile();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("UpdatedStudentProfile", Parcels.wrap(editedprofile));
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        if (view.getId() == R.id.delete){
            StudentProfile deletedProfile = deleteProfile();

        }

    }

    public StudentProfile deleteProfile(){
        HttpDeleteRequests del = new HttpDeleteRequests(mID, DELETE_STUDENT_PROFILE,this );
        del.execute("");
        return new StudentProfile(null, null, null, null,null,null,null,null,null,null, null,null);
    }


    public StudentProfile updateProfile() {
        String firstname = mFirstName.getText().toString();
        String lastname = mLastName.getText().toString();
        String gender = mGender.getText().toString();
        String id = mID.getText().toString();
        String schoolid = mSchoolId.getText().toString();
        String birthdate = mDateOfBirth.getText().toString();
        String guardian = mGuardian.getText().toString();
        String classname = mClassName.getText().toString();
        String telephone = mTelephone.getText().toString();
        String nationalid = mNationalID.getText().toString();
        String grade = mAverageGrade.getText().toString();
        String shoesize = mShoesize.getText().toString();
        HashMap<String, String> studentProfile = new HashMap<>();
        studentProfile.put("firstname", firstname);
        studentProfile.put("lastname", lastname);
        studentProfile.put("gender", gender);
        studentProfile.put("id", id);
        studentProfile.put("schoolid", schoolid);
        studentProfile.put("birthdate", birthdate);
        studentProfile.put("guardian", guardian);
        studentProfile.put("classname", classname);
        studentProfile.put("contact", telephone);
        studentProfile.put("nationalid", nationalid);
        studentProfile.put("grade", grade);
        studentProfile.put("shoesize", shoesize);
        HttpPutRequests task = new HttpPutRequests(studentProfile, PUT_STUDENT_PROFILE, this, this);
        task.execute("");
        return new StudentProfile(firstname, lastname, gender, id, schoolid, birthdate, guardian,
                classname, telephone, nationalid, grade, shoesize);
    }

    public void setProfile(StudentProfile curProfile) {
        mFirstName.setText(curProfile.getFirstName());
        mLastName.setText(curProfile.getLastName());
        mGender.setText(curProfile.getGender());
        mID.setText(curProfile.getId());
        mSchoolId.setText(curProfile.getId());
        mDateOfBirth.setText(curProfile.getDOB());
        mGuardian.setText(curProfile.getGuardian());
        mClassName.setText(curProfile.getClassname());
        mTelephone.setText(curProfile.getTelephone());
        mNationalID.setText(curProfile.getNationalID());
        mAverageGrade.setText(curProfile.getAvegrade());
        mShoesize.setText(curProfile.getShoesize());
    }

    public void onCompletionHandler(boolean success, int requestcode, Object object) {}
}

