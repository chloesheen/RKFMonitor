package com.example.app.asynctasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Student;
import com.example.app.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.app.util.Constants.PUT_STUDENT_ATTENDANCE;
import static com.example.app.util.Constants.PUT_STUDENT_PROFILE;
import static com.example.app.util.Constants.PUT_TEACHER_PROFILE;
import static com.example.app.util.Constants.UPDATE_PASSWORD;

public class HttpPutRequests extends AsyncTask<String, Void, Void> {

    private SharedPreferences mSharedPreferences;
    private JSONObject mPutData;
    private int mRequestCode;
    private CallbackListener mListener;

    public HttpPutRequests(HashMap<String, String> data, int requestcode, CallbackListener listener) {
        mPutData = new JSONObject(data);
        mRequestCode = requestcode;
        mListener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection;
        OutputStreamWriter outputStream;
        InputStream inputStream;
        ByteArrayOutputStream arrayOutputStream;
        String authorization = mSharedPreferences.getString("webtoken", "No Authorization");

        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Authorization", authorization);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK && mPutData != null) {
                outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStream.write(mPutData.toString());
                outputStream.close();

                switch (mRequestCode) {
                    case PUT_STUDENT_PROFILE:
                        mListener.onCompletionHandler(true, PUT_STUDENT_PROFILE, null);
                        break;

                    case PUT_TEACHER_PROFILE:
                        mListener.onCompletionHandler(true, PUT_TEACHER_PROFILE, null);
                        break;

                    case UPDATE_PASSWORD:
                        try {
                            inputStream = new BufferedInputStream(urlConnection.getInputStream());
                            arrayOutputStream = new ByteArrayOutputStream(); //reading the output into this byte array
                            int bytesread;
                            while ((bytesread = inputStream.read()) != -1) {
                                arrayOutputStream.write(bytesread);   //write the byte to the arrayoutputstream
                            }
                            String updatePassword = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                            JSONObject changeMessage = (JSONObject) new JSONTokener(updatePassword).nextValue();
                            boolean succ = changeMessage.getBoolean("success");
                            String message = changeMessage.getString("message");
                            mListener.onCompletionHandler(succ, UPDATE_PASSWORD, message);
                        } catch (Exception e) {e.printStackTrace();}
                        break;

                    case PUT_STUDENT_ATTENDANCE:
                        try {
                            inputStream = new BufferedInputStream(urlConnection.getInputStream());
                            arrayOutputStream = new ByteArrayOutputStream(); //reading the output into this byte array
                            int bytesread;
                            while ((bytesread = inputStream.read()) != -1) {
                                arrayOutputStream.write(bytesread);   //write the byte to the arrayoutputstream
                            }
                            ArrayList<Student> mStudents = new ArrayList<>();
                            String studentString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                            JSONTokener studenttoken = new JSONTokener(studentString);
                            JSONArray studentsArray = new JSONArray(studenttoken);
                            for (int i = 0; i < studentsArray.length(); i++) {
                                JSONObject stud = studentsArray.getJSONObject(i);
                                Student std = new Student(stud.getString("firstname"),
                                        stud.getString("lastname"),
                                        stud.getString("id"),
                                        stud.getBoolean("attending"));
                                mStudents.add(std);
                            }
                            mListener.onCompletionHandler(true, PUT_STUDENT_ATTENDANCE, mStudents);
                        } catch (Exception e) {e.printStackTrace();}
                        break;
                }
            }
        } catch (Exception e) {e.printStackTrace();}

        return null;
    }
}
