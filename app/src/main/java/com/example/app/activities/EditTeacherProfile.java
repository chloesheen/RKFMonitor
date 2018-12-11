package com.example.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.R;
import com.example.app.asynctasks.HttpPutRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.TeacherProfile;

import org.parceler.Parcels;

import java.util.HashMap;

import static com.example.app.util.Constants.PUT_TEACHER_PROFILE;

public class EditTeacherProfile extends AppCompatActivity implements View.OnClickListener, CallbackListener {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mGender;
    private EditText mSchoolId;
    private EditText mNationalId;
    private EditText mContactNum;
    private EditText mClassName;
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
        mGender = (EditText) findViewById(R.id.teach_gender);

        TeacherProfile input = Parcels.unwrap(getIntent().
                getParcelableExtra("CurrentTeacherProfile"));
        setProfile(input);

        mSave = (Button) findViewById(R.id.save);
        mSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save) {
            TeacherProfile editedprofile = updateProfile();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("UpdatedTeacherProfile", Parcels.wrap(editedprofile));
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

    }

    /**
     * This creates the updated teacher profile object that is passed back to the TeacherProfileActivity
     * It also creates a hashmap with the new data and makes a put request to update the database
     * @return The updated TeacherProfile
     */
    public TeacherProfile updateProfile() {
        String firstname = mFirstName.getText().toString();
        String lastname = mLastName.getText().toString();
        String gender = mGender.getText().toString();
        String schoolid = mSchoolId.getText().toString();
        String nationalid = mNationalId.getText().toString();
        String contact = mContactNum.getText().toString();
        String classname = mClassName.getText().toString();
        HashMap<String, String> teacherProfile = new HashMap<>();
        teacherProfile.put("firstname", firstname);
        teacherProfile.put("lastname", lastname);
        teacherProfile.put("gender", gender);
        teacherProfile.put("classname", "");
        teacherProfile.put("contact", contact);
        teacherProfile.put("nationalid", nationalid);
        HttpPutRequests task = new HttpPutRequests(teacherProfile, PUT_TEACHER_PROFILE, this, this);
        task.execute("");
        return new TeacherProfile(firstname, lastname, schoolid, gender,
                classname, contact, nationalid);
    }


    /**
     * Called on the input from the TeacherProfile activity to update the EditActivity view
     * on what the current profile looks like before edits are made
     * @param curProfile
     */
    public void setProfile(TeacherProfile curProfile) {
        mFirstName.setText(curProfile.getFirstName());
        mLastName.setText(curProfile.getLastName());
        mGender.setText(curProfile.getGender());
        mSchoolId.setText(curProfile.getId());
        mNationalId.setText(curProfile.getNationalID());
        mContactNum.setText(curProfile.getTelephone());
        mClassName.setText(curProfile.getClassname());
    }

    /**
     * Handles the return of the put request.
     * Doesn't need to do anything in particular
     * @param success The boolean value of the request
     * @param requestcode The type of request
     * @param object The object returned after the request is made
     */
    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object) {}
}
