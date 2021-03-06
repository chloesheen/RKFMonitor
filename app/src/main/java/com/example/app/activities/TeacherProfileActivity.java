package com.example.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.models.TeacherProfile;

import org.parceler.Parcels;

import static com.example.app.util.Constants.GET_TEACHER_PROFILE;
import static com.example.app.util.Constants.REQUEST_TEACHER_PROFILE;
import static com.example.app.util.Constants.SHARED_PREFS_KEY;

public class TeacherProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private final static int REQUEST_EDIT_PROFILE = 101;

    private TextView mProfileName;
    private EditText mGender;
    private EditText mSchoolId;
    private EditText mNationalId;
    private EditText mContactNum;
    private EditText mClassName;
    private Button mLogout;

    //private SharedPreferences mSharedPreferences = this.getSharedPreferences("SHARED_PREFS_KEY", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TeacherProfile profile = Parcels.unwrap(getIntent().getParcelableExtra("TeacherProfile"));

        mSchoolId = (EditText) findViewById(R.id.school_id);
        mNationalId = (EditText) findViewById(R.id.national_id);
        mContactNum = (EditText) findViewById(R.id.contact_num);
        mClassName = (EditText) findViewById(R.id.class_name);
        mGender = (EditText) findViewById(R.id.teach_gender);
        mProfileName = (TextView) findViewById(R.id.name);

        mLogout = (Button) findViewById(R.id.logout);
        mLogout.setFocusable(true);
        mLogout.setOnClickListener(this);

        TextView mSchoolName = (TextView) findViewById(R.id.schoolName);
        //mSchoolName.setText(mSharedPreferences.getString("school", null));

        populateView(profile);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_edit:
                TeacherProfile currentProfile = currentProfile();
                Intent intent = new Intent (this, EditTeacherProfile.class);
                intent.putExtra("CurrentTeacherProfile", Parcels.wrap(currentProfile));
                startActivityForResult(intent, REQUEST_EDIT_PROFILE);
                break;

            case R.id.action_exit:
                Intent homeintent = new Intent(this, ClassViewActivity.class);
                startActivity(homeintent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Populates the teacher profile When the profile is first opened
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
    }

    /**
     * Starts the activity for editing the profile when the edit button is clicked
     * It starts the activity and gets back the updated teachers profile as a result
     * @param view The edit button view
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout) {
            mLogout.requestFocus();
            SharedPreferences preferences = getSharedPreferences(SHARED_PREFS_KEY,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
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
        return new TeacherProfile(firstname, lastname, schoolid, gender,
                classname, contact, nationalid);
    }

}
