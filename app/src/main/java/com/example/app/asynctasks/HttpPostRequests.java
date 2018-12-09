package com.example.app.asynctasks;

import android.os.AsyncTask;

import com.example.app.interfaces.CallbackListener;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.example.app.util.Constants.POST_FOOD;
import static com.example.app.util.Constants.POST_NEW_STUDENT;
import static com.example.app.util.Constants.REQUEST_ADD_FOOD;
import static com.example.app.util.Constants.REQUEST_ADD_NEW_STUDENT;
import static com.example.app.util.Constants.REQUEST_REGISTER_TEACHER;

public class HttpPostRequests extends AsyncTask<String, Void, Void> {

    JSONObject mPostData;
    int mRequestType;
    CallbackListener mListener;

    public HttpPostRequests(HashMap<String, String> post, int request, CallbackListener listener) {
        mPostData = new JSONObject(post);
        mRequestType = request;
        mListener = listener;
    }

    @Override
    public Void doInBackground(String...params) {
        URL url;
        HttpURLConnection urlConnection;
        OutputStreamWriter outputStream;
        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", "authorization");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK  && mPostData != null) {
                outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStream.write(mPostData.toString());
                outputStream.flush();

                switch(mRequestType) {
                    case POST_NEW_STUDENT:
                        mListener.onCompletionHandler(true, POST_NEW_STUDENT, null);
                        break;

                    case POST_FOOD:
                        mListener.onCompletionHandler(true, POST_FOOD, null);
                        break;
                }
            }
        } catch(Exception e) {e.printStackTrace();}


        return null;
    }



    private String postDataString(HashMap<String, String> postdata) {
        StringBuilder postData = new StringBuilder();
        try {
            for(Map.Entry<String, String> param : postdata.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

        }catch(Exception e) {e.printStackTrace();}

        return postData.toString();
    }
}
