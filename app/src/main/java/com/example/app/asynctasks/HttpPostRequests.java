package com.example.app.asynctasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.app.interfaces.CallbackListener;
import com.example.app.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import android.os.AsyncTask;
import android.util.Log;

import com.example.app.interfaces.CallbackListener;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import static android.content.Context.MODE_PRIVATE;
import static com.example.app.util.Constants.POST_FOOD;
import static com.example.app.util.Constants.POST_LOGIN;
import static com.example.app.util.Constants.POST_FOOD;
import static com.example.app.util.Constants.POST_NEW_STUDENT;
import static com.example.app.util.Constants.REQUEST_ADD_FOOD;
import static com.example.app.util.Constants.REQUEST_ADD_NEW_STUDENT;
import static com.example.app.util.Constants.REQUEST_REGISTER_TEACHER;

import static com.example.app.util.Constants.SHARED_PREFS_KEY;

public class HttpPostRequests extends AsyncTask<String, Void, Void> {

    private JSONObject mPostData;
    private int mRequestType;
    private CallbackListener mListener;
    private Context mContext;

    public HttpPostRequests(HashMap<String, String> post, int request, CallbackListener listener, Context context) {
        mPostData = new JSONObject(post);
        mRequestType = request;
        mListener = listener;
        mContext = context;
    }

    @Override
    public Void doInBackground(String...params) {
        URL url;
        HttpURLConnection urlConnection;
        OutputStreamWriter outputStream;
        InputStream inputStream;
        ByteArrayOutputStream arrayOutputStream;

        SharedPreferences getAuthorization = mContext.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        String authorization = getAuthorization.getString("authorization", null);

        Log.v("urlforpost", params[0]);

        try {
            Log.v("inthetryforpost", "woah");
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            //urlConnection.setRequestProperty("Authorization", authorization);
            urlConnection.setRequestProperty("Content-Type", "application/json");



            outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStream.write(mPostData.toString());
            outputStream.flush();
            Log.v("requestbodyforpost", mPostData.toString());
            Log.v("responseforpost", String.valueOf(urlConnection.getResponseCode()));
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK  && mPostData != null) {

                switch(mRequestType) {
                    case POST_NEW_STUDENT:
                        mListener.onCompletionHandler(true, POST_NEW_STUDENT, null);
                        break;

                    case POST_FOOD:
                        mListener.onCompletionHandler(true, POST_FOOD, null);
                        break;

                    case POST_LOGIN:
                        try {
                            Log.v("test4", "hello");
                            inputStream = new BufferedInputStream(urlConnection.getInputStream());
                            arrayOutputStream = new ByteArrayOutputStream(); //reading the output into this byte array
                            int bytesread;
                            while ((bytesread = inputStream.read()) != -1) {
                                arrayOutputStream.write(bytesread);   //write the byte to the arrayoutputstream
                            }
                            String logininfo = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                            JSONObject loginMessage = (JSONObject) new JSONTokener(logininfo).nextValue();
                            boolean succ = loginMessage.getBoolean("success");
                            String webtoken = loginMessage.getString("JWT");
                            String isAdmin  = String.valueOf(loginMessage.getBoolean("isAdministrator"));
                            Log.v("admin", isAdmin);
                            String isTeacher = String.valueOf(loginMessage.getBoolean("isTeacher"));
                            String isCook = String.valueOf(loginMessage.getBoolean("isCook"));
                            String school = loginMessage.getString("school");
                            String classname = loginMessage.getString("class");
                            ArrayList<Pair<String, String>> res = new ArrayList<>();
                            Log.v("token", webtoken);
                            res.add(new Pair("token", webtoken));
                            res.add(new Pair("isAdmin",isAdmin));
                            res.add(new Pair("isTeacher", isTeacher));
                            res.add(new Pair("isCook", isCook));
                            res.add(new Pair("school", school));
                            res.add(new Pair("class", classname));
                            mListener.onCompletionHandler(succ, POST_LOGIN, res);
                        } catch (Exception e) {e.printStackTrace();}
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
