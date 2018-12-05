package com.example.app.asynctasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.app.activities.StudentProfileActivity;
import com.example.app.models.StudentProfile;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Asynctask to retrieve student profile from database
 * Launches the StudentProfile activity and passes it the studentProfile object.
 * Activity should be built from the object.
 */
public class GetStudentProfile extends AsyncTask<String, Void, StudentProfile> {

    private SharedPreferences mSharedPreferences;

    private StudentProfile stdProfile;

    @Override
    protected StudentProfile doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection;
        InputStream inputStream;
        ByteArrayOutputStream arrayOutputStream;
        String authorization = mSharedPreferences.getString("webtoken", "No Authorization"); //change this
        String studentString;
        String studentprofileurl = params[0];


        try {
            //open connection to the server
            url = new URL(studentprofileurl);
            urlConnection = (HttpURLConnection) url.openConnection();

            //set the request properties i.e what type of request and the header metadata required
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", authorization);
            urlConnection.setRequestProperty("Content-Type", "application/json");

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
                stdProfile = new StudentProfile(student.getString("firstname"),
                        student.getString("lastname"),
                        student.getString("id"),
                        student.getString("studentid"),
                        student.getString("gender"),
                        student.getString("dateofbirth"),
                        student.getString("classname"),
                        student.getString("guardian"),
                        student.getInt("telephone"),
                        student.getInt("nationalid"),
                        student.getInt("avegrade"),
                        student.getInt("shoesize"));

            } else { throw new IOException(urlConnection.getResponseMessage() + ": with" + ""); }

        } catch (Exception e) { e.printStackTrace();}

        return stdProfile;
    }

}