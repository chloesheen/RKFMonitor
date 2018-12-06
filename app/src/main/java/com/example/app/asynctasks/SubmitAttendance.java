package com.example.app.asynctasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.app.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SubmitAttendance extends AsyncTask<String, Void, Void> {

    private SharedPreferences mSharedPreferences;

    @Override
    protected Void doInBackground(String...params) {
        URL url;
        HttpURLConnection urlConnection;
        OutputStreamWriter outputStream;
        String authorization = mSharedPreferences.getString("webtoken", "No Authorization");
        String baseurl = params[0];

        try {
            url = new URL(baseurl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Authorization", authorization);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStream.write(params[1]);
                outputStream.close();
            }
        } catch(Exception e) {e.printStackTrace();}

        return null;
    }
}
