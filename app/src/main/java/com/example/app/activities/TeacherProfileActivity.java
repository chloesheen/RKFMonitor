package com.example.app.activities;

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

import com.example.app.R;
import com.example.app.models.TeacherProfile;

public class TeacherProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mEdit;
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
        setContentView(R.layout.activity_teacher_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEdit = (ImageView) findViewById(R.id.edit);
        mEdit.setOnClickListener(this);

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
        switch(view.getId()) {
            case R.id.edit:
                mSchoolId.setEnabled(true);
                mNationalId.setEnabled(true);
                mContactNum.setEnabled(true);
                mClassName.setEnabled(true);
                mStream.setEnabled(true);
                mGender.setEnabled(true);
                mSave.setVisibility(View.VISIBLE);
                break;
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public TeacherProfile updateProfile() {
        TeacherProfile updatedProfile = new TeacherProfile();
        return updatedProfile;
    }
}
