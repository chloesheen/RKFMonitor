package com.example.app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.StudentListAdapter;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Student;
import com.example.app.models.StudentProfile;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ClassViewActivity extends AppCompatActivity implements OnItemClickListener,
        View.OnClickListener{

    private ArrayList<Student> mStudents;
    private RecyclerView.LayoutManager mLayoutManager;
    private StudentListAdapter mStudentListAdapter;
    private RecyclerView mStudentView;
    private OnItemClickListener mListener;

    private Button mAddStudents;
    private TextView mProfileName;

    private SharedPreferences mSharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStudentView = (RecyclerView) findViewById(R.id.student_list_view);

        mLayoutManager = new LinearLayoutManager(this);
        mStudentView.setLayoutManager(mLayoutManager);

        mStudentListAdapter = new StudentListAdapter(mStudents, mListener);
        mStudentView.setAdapter(mStudentListAdapter);

        mAddStudents = (Button) findViewById(R.id.add_student);
        mAddStudents.setOnClickListener(this);

        mProfileName = (TextView) findViewById(R.id.profile_name);
        mProfileName.setOnClickListener(this);
    }



    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_student:
                Intent intent = new Intent(this, AddStudentActivity.class);
                startActivity(intent);
                break;

            case R.id.profile_name:
                Intent profileIntent = new Intent(this, TeacherProfileActivity.class);
                startActivity(profileIntent);
        }
    }


    //Asynctask to retrieve student profile from database
    //Launches the StudentProfile activity and passes it the studentProfile object.
    //Activity should be built from the object.
    private class GetStudentProfile extends AsyncTask<String, Void, StudentProfile> {

        private StudentProfile stdProfile;

        @Override
        protected StudentProfile doInBackground(String... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            ByteArrayOutputStream arrayOutputStream = null;
            String authorization = mSharedPreferences.getString("webtoken", "No Authorization"); //change this
            String studentString;
            String id = params[0];



            try {
                //open connetion to the server
                url = new URL("");
                urlConnection = (HttpURLConnection) url.openConnection();

                //set the request properties i.e what type of request and the header metadata required
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization", authorization); //don't know if we need this
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("StudentID", id);

                //Read in the data
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    arrayOutputStream = new ByteArrayOutputStream(); //reading the output into this byte array
                    int bytesread;
                    while((bytesread = inputStream.read()) != -1) {
                        arrayOutputStream.write(bytesread);   //write the byte to the arrayoutputstream
                    }

                    //creates student object from json and adds to student list
                    studentString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                    JSONTokener token = new JSONTokener(studentString);
                    JSONObject student = (JSONObject) token.nextValue();
                    stdProfile = new StudentProfile (student.getString("firstname"),
                            student.getString("lastname"),
                            student.getString("id"),
                            student.getString("gender"),
                            student.getString("dateofbirth"),
                            student.getString("classname"),
                            student.getString("guardian"),
                            student.getInt("telephone"),
                            student.getInt("nationalid"),
                            student.getInt("avegrade"),
                            student.getInt("shoesize"));
                    return stdProfile;
                    } else { throw new IOException(urlConnection.getResponseMessage() + ": with" + ""); }

                } catch (Exception e) { e.printStackTrace();}
                return null;
        }


        @Override
        protected void onPostExecute(StudentProfile stdProfile) {
            Intent intent = new Intent(getApplicationContext(), StudentProfileActivity.class);
            intent.putExtra("Studentprofile", Parcels.wrap(stdProfile));
            startActivity(intent);
        }

    }
}
