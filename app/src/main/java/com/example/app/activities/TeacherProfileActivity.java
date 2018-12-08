package com.example.app.activities;

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
import com.example.app.models.TeacherProfile;

import org.parceler.Parcels;

public class TeacherProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private final static int REQUEST_EDIT_PROFILE = 101;

    private ImageView mEdit;
    private TextView mProfileName;
    private EditText mGender;
    private EditText mSchoolId;
    private EditText mNationalId;
    private EditText mContactNum;
    private EditText mClassName;
    private EditText mStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TeacherProfile profile = Parcels.unwrap(getIntent().getParcelableExtra("TeacherProfile"));

        mEdit = (ImageView) findViewById(R.id.edit);
        mEdit.setOnClickListener(this);

        mSchoolId = (EditText) findViewById(R.id.school_id);
        mNationalId = (EditText) findViewById(R.id.national_id);
        mContactNum = (EditText) findViewById(R.id.contact_num);
        mClassName = (EditText) findViewById(R.id.class_name);
        mStream = (EditText) findViewById(R.id.stream_name);
        mGender = (EditText) findViewById(R.id.teach_gender);
        mProfileName = (TextView) findViewById(R.id.name);

        populateView(profile);
    }

    /**
     * Populates the teacher profile When the profile is opened
     * @param profile The Teachers profile
     */

    public void populateView(TeacherProfile profile) {
        String fullname = profile.getFirstName() + " " + profile.getLastName();
        mProfileName.setText(fullname);
        mSchoolId.setText(profile.getId());
        mGender.setText(profile.getGender());
        mClassName.setText(profile.getClassname());
        mContactNum.setText(profile.getTelephone());
        mNationalId.setText(profile.getNationalID());
        mStream.setText(profile.getStream());
    }

    /**
     * Starts the activity for editing the profile whe the edit button is clicked
     * It starts the activity and gets back the updated teachers profile as a result
     * @param view The edit button view
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.edit){
            TeacherProfile currentProfile = currentProfile();
            Intent intent = new Intent (this, EditTeacherProfile.class);
            intent.putExtra("CurrentTeacherProfile", Parcels.wrap(currentProfile));
            startActivityForResult(intent, REQUEST_EDIT_PROFILE);
        }
    }

    /**
     * Called after the EditTeacherProfile activity returns.
     * Updates the view to reflect the edits made
     * @param requestcode The requst code of the EditProfileActivity
     * @param resultcode  The result code returned from the EditProfileActivity
     * @param data        The Updated Teache rProfile contained in an intent
     */
    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        if (requestcode == REQUEST_EDIT_PROFILE && resultcode == Activity.RESULT_OK) {
            TeacherProfile result = Parcels.unwrap(data.getParcelableExtra("UpdatedTeacherProfile"));
            String profilename = result.getFirstName() + " " + result.getLastName();
            if (profilename != (mProfileName.getText())) {
                mProfileName.setText(profilename);
            }
            if (!(mGender.getText().toString().equals(result.getGender()))) {
                mGender.setText(result.getGender());
            }
            if (!(mSchoolId.getText().toString().equals(result.getId()))) {
                mSchoolId.setText(result.getId());
            }
            if (!(mNationalId.getText().toString().equals(result.getNationalID()))) {
                mNationalId.setText(result.getNationalID());
            }
            if (!(mContactNum.getText().toString().equals(result.getTelephone()))) {
                mContactNum.setText(result.getTelephone());
            }
            if (!(mClassName.getText().toString().equals(result.getClassname()))) {
                mClassName.setText(result.getClassname());
            }
            if (!(mStream.getText().toString().equals(result.getStream()))) {
                mStream.setText(result.getStream());
            }
        }
    }


    /**
     * Creates a TeacherProfile object to be passes to the EditTeacherProfileActivity
     * @return The TeacherProfile object
     */

    public TeacherProfile currentProfile() {
        String[] fullname = mProfileName.getText().toString().split("\\s+");
        String firstname = fullname[0];
        String lastname = fullname[1];
        String gender = mGender.getText().toString();
        String schoolid = mSchoolId.getText().toString();
        String nationalid = mNationalId.getText().toString();
        String contact = mContactNum.getText().toString();
        String classname = mClassName.getText().toString();
        String stream = mStream.getText().toString();
        return new TeacherProfile(firstname, lastname, schoolid, gender,
                classname, contact, nationalid, stream);
    }

}
