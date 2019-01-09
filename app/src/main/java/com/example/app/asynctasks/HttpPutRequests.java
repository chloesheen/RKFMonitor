package com.example.app.asynctasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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

import static android.content.Context.MODE_PRIVATE;
import static com.example.app.util.Constants.GET_STUDENTLIST_VIEW;
import static com.example.app.util.Constants.PUT_FOOD;
import static com.example.app.util.Constants.PUT_STUDENT_ATTENDANCE;
import static com.example.app.util.Constants.PUT_STUDENT_PROFILE;
import static com.example.app.util.Constants.PUT_TEACHER_PROFILE;
import static com.example.app.util.Constants.SHARED_PREFS_KEY;
import static com.example.app.util.Constants.UPDATE_PASSWORD;

public class HttpPutRequests extends AsyncTask<String, Void, Void> {

    private JSONObject mPutData;
    private int mRequestCode;
    private CallbackListener mListener;
    private Context mContext;

    //Have this take a Hashmap of string and string (use request code to convert data to jsonArray as required)
    /*public HttpPutRequests(HashMap<String, JSONArray> data, int requestcode, CallbackListener listener, Context context) {
        mPutData = new JSONObject(data);
        mRequestCode = requestcode;
        mListener = listener;
        mContext = context;
    }*/

    /**
     * Generic constructor for put request since the requests have different bodies
     * @param data A haspmap containing the data to be put into the db
     * @param requestcode Request code for the type of request being made
     * @param listener    The activity that is calling the request (implements the CallbackListener interface)
     * @param context     The activity the request is being called from
     * @param <T>         A generic parameter for the type of value being used in the hashmap (eg. JSONArray, JSONObject, String etc)
     */

    public <T> HttpPutRequests(HashMap<String, T> data, int requestcode, CallbackListener listener, Context context) {
        mPutData = new JSONObject(data);
        Log.v("attendancedata", mPutData.toString());
        mRequestCode = requestcode;
        mListener = listener;
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection;
        OutputStreamWriter outputStream;
        InputStream inputStream;
        ByteArrayOutputStream arrayOutputStream;

        //SharedPreferences getAuthorization = mContext.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        //String authorization = getAuthorization.getString("authorization", null);
        String authorization = "JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVjMjI0NmNkNWQ4NWE0MThmY2NhNzNmZCJ9LCJpYXQiOjE1NDcwMjIwMjB9.tkgIyvJFFB9eB45MOrweEUNyA0Yg9XML-EYBRk1_1yw";

        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Authorization", authorization);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            outputStream = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStream.write(mPutData.toString());
            outputStream.close();

            Log.v("putdate", mPutData.toString());
            Log.v("putresponse", String.valueOf(urlConnection.getResponseCode()));

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK && mPutData != null) {
                switch (mRequestCode) {
                    case PUT_STUDENT_PROFILE:
                        mListener.onCompletionHandler(true, PUT_STUDENT_PROFILE, null);
                        break;

                    case PUT_TEACHER_PROFILE:
                        try {
                            inputStream = new BufferedInputStream(urlConnection.getInputStream());
                            arrayOutputStream = new ByteArrayOutputStream(); //reading the output into this byte array
                            int bytesread;
                            while ((bytesread = inputStream.read()) != -1) {
                                arrayOutputStream.write(bytesread);   //write the byte to the arrayoutputstream
                            }
                            String updateTeachProfile = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                            JSONObject changeMessage = (JSONObject) new JSONTokener(updateTeachProfile).nextValue();
                            boolean succ = changeMessage.getBoolean("success");
                            mListener.onCompletionHandler(succ, PUT_TEACHER_PROFILE, null);
                        } catch (Exception e) {e.printStackTrace();}
                        break;

                    case PUT_FOOD:
                        try {
                            inputStream = new BufferedInputStream(urlConnection.getInputStream());
                            arrayOutputStream = new ByteArrayOutputStream(); //reading the output into this byte array
                            int bytesread;
                            while ((bytesread = inputStream.read()) != -1) {
                                arrayOutputStream.write(bytesread);   //write the byte to the arrayoutputstream
                            }
                            String updateFood = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                            JSONObject requestresult = (JSONObject) new JSONTokener(updateFood).nextValue();
                            boolean succ = requestresult.getBoolean("success");
                            mListener.onCompletionHandler(succ, PUT_FOOD, null);
                        } catch (Exception e) {e.printStackTrace();}
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
                            Log.v("issuccess", String.valueOf(succ));
                            String message = changeMessage.getString("message");
                            Log.v("message", message);
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
                            String successString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                            JSONObject successObj = (JSONObject) new JSONTokener(successString).nextValue();
                            Boolean success = successObj.getBoolean("success");
                            Log.v("issuccess", String.valueOf(success));
                            mListener.onCompletionHandler(true, PUT_STUDENT_ATTENDANCE, null);
                        } catch (Exception e) {e.printStackTrace();}
                        break;
                }
            }
        } catch (Exception e) {e.printStackTrace();}

        return null;
    }
}
