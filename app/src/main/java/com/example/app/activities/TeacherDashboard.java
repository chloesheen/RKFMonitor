package com.example.app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.app.R;
import com.example.app.adapters.ClassListAdapter;
import com.example.app.interfaces.OnItemClickListener;
import com.example.app.models.Class;
import com.example.app.models.Student;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TeacherDashboard extends AppCompatActivity implements OnItemClickListener {

    private ArrayList<Class> mClasses = new ArrayList<>();
    private ArrayList<Student> mStudents = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;
    private SharedPreferences mSharedPreferences;

    // * TODO: Add layout manager to this
    // * @param
    //  *@return
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;

        RecyclerView mClassList = (RecyclerView) findViewById(R.id.class_list_view);

        ClassListAdapter mClassListAdapter = new ClassListAdapter(this, mClasses, mListener);

        mClassList.setAdapter(mClassListAdapter);
    }

    //currently only works for classes not for selecting teachers profile
    //executes asynctask which gets student list for selected class
    @Override
    public void onItemClick(int position) {
        GetStudents task = new GetStudents();
        task.execute();
    }


    //Asynctask to retrieve list of students for database when a class is selected.
    //Parses the JSON objects and creates Student objects with them. Adds student obj to arraylist
    public class GetStudents extends AsyncTask<Void, Void, ArrayList<Student>> {


        protected ArrayList<Student> doInBackground(Void... params){
            URL url = null;
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            ByteArrayOutputStream arrayOutputStream = null;
            String authorization = mSharedPreferences.getString("webtoken", "No Authorization");
            String studentString;

            try {
                //open connetion to the server
                url = new URL("");
                urlConnection = (HttpURLConnection) url.openConnection();

                //set the request properties i.e what type of request and the header metadata required
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Authorization", authorization);

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
                    JSONArray studentsArray = new JSONArray(token);
                    for (int i = 0; i < studentsArray.length(); i++) {
                        JSONObject student = studentsArray.getJSONObject(i);
                        Student std = new Student(student.getString("firstname"),
                                student.getString("lastname"),
                                student.getString("id"),
                                student.getBoolean("attending"));
                        mStudents.add(std);
                    }
                } else {
                    throw new IOException(urlConnection.getResponseMessage() + ": with" + "");
                }

            } catch (Exception e) { e.printStackTrace();}

            return mStudents;
        }

        //starts the activity that displays the list of students in the class.
        protected void onPostExecute(ArrayList... params) {
            Intent intent = new Intent(mContext, ClassViewActivity.class);
            intent.putExtra("StudentInfo", mStudents);
            startActivity(intent);
        }
    }

}
