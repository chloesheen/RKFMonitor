package com.example.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.R;

public class EditTeacherProfile extends AppCompatActivity implements View.OnClickListener{

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mGender;
    private EditText mSchoolId;
    private EditText mNationalId;
    private EditText mContactNum;
    private EditText mClassName;
    private EditText mStream;
    private Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher_profile);

        mFirstName = (EditText) findViewById(R.id.firstname_box);
        mLastName = (EditText) findViewById(R.id.lastname_box);
        mSchoolId = (EditText) findViewById(R.id.school_id);
        mNationalId = (EditText) findViewById(R.id.national_id);
        mContactNum = (EditText) findViewById(R.id.contact_num);
        mClassName = (EditText) findViewById(R.id.class_name);
        mStream = (EditText) findViewById(R.id.stream_name);
        mGender = (EditText) findViewById(R.id.teach_gender);

        mSave = (Button) findViewById(R.id.save);
    }

    @Override
    public void onClick(View view) {

    }
}
