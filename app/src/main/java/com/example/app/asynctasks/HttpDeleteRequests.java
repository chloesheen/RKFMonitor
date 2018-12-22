package com.example.app.asynctasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;

import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Student;
import com.example.app.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;


public class HttpDeleteRequests extends AsyncTask<String, Void, Void> {


    private int mRequestCode;
    private CallbackListener mListener;
    EditText student_id;

    public HttpDeleteRequests(EditText studentID, int requestcode, CallbackListener listener) {
        mRequestCode = requestcode;
        mListener = listener;
        student_id = studentID;
    }

    @Override
    protected Void doInBackground(String... strings) {
        //URL url;
        //HttpURLConnection urlConnection;
        InputStream inputStream;

        URL url = null;
        try {
            String id = student_id.getText().toString();
            url = new URL("https://kotak.herokuapp.com/teachers/students/"+id);

            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Authorization", "authorization");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("DELETE");

            urlConnection.connect();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
