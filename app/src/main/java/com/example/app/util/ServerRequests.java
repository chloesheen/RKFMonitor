package com.example.app.util;

import android.os.AsyncTask;
import android.os.Parcelable;

import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.app.util.Constants.GET_REQUEST;
import static com.example.app.util.Constants.POST_REQUEST;
import static com.example.app.util.Constants.PUT_REQUEST;

public class ServerRequests extends AsyncTask<String, Void, Parcelable> {


    @Override
    public Parcelable doInBackground(String... params) {
        String requestype = params[0];
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream arrayOutputStream = null;
        String authorization = "yes"; //temporary
        try {
            URL url = new URL(params[2]);
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {e.printStackTrace(); }


        switch (requestype) {
            case GET_REQUEST:

                try {
                    //set the request properties i.e what type of request and the header metadata required
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Authorization", authorization); //don't know if we need this
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    //Read in the data
                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        inputStream = new BufferedInputStream(urlConnection.getInputStream());
                        arrayOutputStream = new ByteArrayOutputStream(); //reading the output into this byte array
                        int bytesread;
                        while ((bytesread = inputStream.read()) != -1) {
                            arrayOutputStream.write(bytesread);   //write the byte to the arrayoutputstream
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case POST_REQUEST:
                break;
            case PUT_REQUEST:
                break;
        }

        return Parcels.wrap("hello");
    }

}
