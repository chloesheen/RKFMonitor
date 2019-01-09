package com.example.app.asynctasks;


import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.app.activities.StudentProfileActivity;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Calendar;
import com.example.app.models.Class;
import com.example.app.models.Food;
import com.example.app.models.School;
import com.example.app.models.Student;
import com.example.app.models.StudentProfile;
import com.example.app.models.TeacherProfile;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.app.util.Constants.GET_CLASSES;
import static com.example.app.util.Constants.GET_DAILY_ATTENDANCE;
import static com.example.app.util.Constants.GET_DAILY_FOOD;
import static com.example.app.util.Constants.GET_MONTHLY_ATTENDANCE;
import static com.example.app.util.Constants.GET_MONTHLY_FOOD;
import static com.example.app.util.Constants.GET_SCHOOLS;
import static com.example.app.util.Constants.GET_STUDENTLIST_VIEW;
import static com.example.app.util.Constants.GET_STUDENT_PROFILE;
import static com.example.app.util.Constants.GET_TEACHER_PROFILE;
import static com.example.app.util.Constants.GET_TOTAL_ATTENDANCE;
import static com.example.app.util.Constants.SHARED_PREFS_KEY;
import static com.example.app.util.DateUtils.convertToDate;
import static com.example.app.util.DateUtils.convertToMonthDate;

/**
 * Asynctask to handle all Get requests
 * Passes result of requests to a callback function that is implemented by all classes that are making the request
 * Activity should be built from the object.
 */
public class HttpGetRequests extends AsyncTask<String, Void, Void> {

    private int mRequestCode;
    private CallbackListener mListener;
    private Context mContext;

    public HttpGetRequests(int requestcode, CallbackListener listener, Context context) {
        mRequestCode = requestcode;
        mListener = listener;
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection;
        InputStream inputStream;
        ByteArrayOutputStream arrayOutputStream;
        //base url
        String baseurl = params[0];

        //SharedPreferences getAuthorization = mContext.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        //String authorization = getAuthorization.getString("authorization", null);
        String authorization = "JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7Il9pZCI6IjVjMjI0NmNkNWQ4NWE0MThmY2NhNzNmZCJ9LCJpYXQiOjE1NDcwMjIwMjB9.tkgIyvJFFB9eB45MOrweEUNyA0Yg9XML-EYBRk1_1yw";
        //Log.v("auth", authorization);
        //Log.v("url", baseurl);


        try {
            //open connection to the server
            url = new URL(baseurl);
            urlConnection = (HttpURLConnection) url.openConnection();

            //set the request properties i.e what type of request and the header metadata required


            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Authorization", authorization);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            //Read in the data
            //Log.v("test5", String.valueOf(urlConnection.getResponseCode()));
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                arrayOutputStream = new ByteArrayOutputStream(); //reading the output into this byte array
                int bytesread;
                while ((bytesread = inputStream.read()) != -1) {
                    arrayOutputStream.write(bytesread);   //write the byte to the arrayoutputstream
                }

                switch (mRequestCode) {

                    case GET_STUDENT_PROFILE:
                        String studentString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONTokener token = new JSONTokener(studentString);
                        JSONObject student = (JSONObject) token.nextValue();
                        StudentProfile stdProfile = new StudentProfile(student.getString("firstname"),
                                student.getString("lastname"),
                                student.getString("id"),
                                student.getString("studentid"),
                                student.getString("gender"),
                                student.getString("dateofbirth"),
                                student.getString("classname"),
                                student.getString("guardian"),
                                student.getString("telephone"),
                                student.getString("nationalid"),
                                student.getString("avegrade"),
                                student.getString("shoesize"));
                        mListener.onCompletionHandler(true, GET_STUDENT_PROFILE, stdProfile);
                        break;

                    case GET_STUDENTLIST_VIEW:
                        ArrayList<Student> mStudents = new ArrayList<>();
                        studentString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        Log.v("json", studentString);
                        JSONObject studentObj = (JSONObject) new JSONTokener(studentString).nextValue();
                        JSONArray studentsArray =  (JSONArray) studentObj.get("students");
                        Log.v("studentlist", studentsArray.toString());
                        for (int i = 0; i < studentsArray.length(); i++) {
                            JSONObject stud = studentsArray.getJSONObject(i);
                            Student std = new Student(stud.getString("firstName"),
                                    stud.getString("lastName"),
                                    stud.getString("id"),
                                    stud.getBoolean("attending"));
                            mStudents.add(std);
                        }
                        Log.v("studentlist", mStudents.toString());
                        mListener.onCompletionHandler(true, GET_STUDENTLIST_VIEW, mStudents);
                        break;

                    case GET_TEACHER_PROFILE:
                        String teacherString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONTokener teachertoken = new JSONTokener(teacherString);
                        JSONObject teacher = (JSONObject) teachertoken.nextValue();
                        TeacherProfile teacherProfile = new TeacherProfile(teacher.getString("firstName"),
                                teacher.getString("lastName"),
                                teacher.getString("id"),
                                teacher.getString("gender"),
                                teacher.getString("schoolid"),
                                teacher.getString("nationalid"),
                                teacher.getString("classname"),
                                teacher.getString("telephone"));

                        mListener.onCompletionHandler(true, GET_TEACHER_PROFILE, teacherProfile);
                        break;

                    case GET_TOTAL_ATTENDANCE:
                        String attendanceString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONTokener attendancetoken = new JSONTokener(attendanceString);
                        JSONObject attendance = (JSONObject) attendancetoken.nextValue();
                        String totalattendance = attendance.getString("numOfStudents");
                        int numofStudents = Integer.valueOf(totalattendance);
                        mListener.onCompletionHandler(true, GET_TOTAL_ATTENDANCE, numofStudents);
                        break;

                    case GET_DAILY_ATTENDANCE:
                        ArrayList<Calendar> dailyCalendars = new ArrayList<>();
                        String schoolattendance = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONObject schoolAttendanceObj = (JSONObject) new JSONTokener(schoolattendance).nextValue();
                        JSONObject dailyattendance =  schoolAttendanceObj.getJSONObject("daily");
                        Iterator<String> dailyIterator = dailyattendance.keys();
                        while (dailyIterator.hasNext()) {
                            String key = dailyIterator.next();
                            dailyCalendars.add(new Calendar(convertToDate(key), dailyattendance.get(key)));
                        }
                        mListener.onCompletionHandler(true, GET_DAILY_ATTENDANCE, dailyCalendars);
                        break;

                    case GET_MONTHLY_ATTENDANCE:
                        ArrayList<Calendar> monthlyCalendars = new ArrayList<>();
                        String schoolattendanceString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONObject attendanceObj = (JSONObject) new JSONTokener(schoolattendanceString).nextValue();
                        JSONObject monthlyattendance =  attendanceObj.getJSONObject("monthly");
                        Iterator<String> monthlyIterator = monthlyattendance.keys();
                        while (monthlyIterator.hasNext()) {
                            String key = monthlyIterator.next();
                            monthlyCalendars.add(new Calendar(convertToMonthDate(key), monthlyattendance.get(key)));
                        }
                        mListener.onCompletionHandler(true, GET_MONTHLY_ATTENDANCE, monthlyCalendars);
                        break;

                    case GET_DAILY_FOOD:
                        ArrayList<Calendar> dailyFeedingCalendars = new ArrayList<>();
                        String dailyFeedingString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONObject feedingObj = (JSONObject) new JSONTokener(dailyFeedingString).nextValue();
                        JSONObject dailyFeeding =  feedingObj.getJSONObject("daily");
                        Iterator<String> feedingIterator = dailyFeeding.keys();
                        while (feedingIterator.hasNext()) {
                            String day = feedingIterator.next();
                            JSONObject mealIngredients = (JSONObject) dailyFeeding.get(day);
                            HashMap<String, String> foodRatios = new HashMap<>();
                            Food ingredientRatios = new Food(mealIngredients.getString("MealType"), foodRatios);
                            Iterator<String> ratios = mealIngredients.keys();
                            ratios.next();
                            while (ratios.hasNext()) {
                                String mealKey = ratios.next();
                                foodRatios.put(mealKey, mealIngredients.getString(mealKey));
                            }
                            dailyFeedingCalendars.add(new Calendar(convertToDate(day), ingredientRatios));
                        }
                        mListener.onCompletionHandler(true, GET_DAILY_FOOD, dailyFeedingCalendars);
                        break;

                    case GET_MONTHLY_FOOD:
                        ArrayList<Calendar> monthlyFeedingCalendars = new ArrayList<>();
                        String monthlyFeedingString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONObject monthlyFeedingObj = (JSONObject) new JSONTokener(monthlyFeedingString).nextValue();
                        JSONObject monthlyFeeding =  monthlyFeedingObj.getJSONObject("monthly");
                        Iterator<String> monthlyFeedingIterator = monthlyFeeding.keys();
                        while (monthlyFeedingIterator.hasNext()) {
                            String key = monthlyFeedingIterator.next();
                            JSONObject mealIngredients = (JSONObject) monthlyFeeding.get(key);
                            HashMap<String, String> foodRatios = new HashMap<>();
                            Food ingredientRatios = new Food(mealIngredients.getString("MealType"), foodRatios);
                            Iterator<String> ratios = mealIngredients.keys();
                            ratios.next();
                            while (ratios.hasNext()) {
                                String mealKey = ratios.next();
                                foodRatios.put(mealKey, mealIngredients.getString(mealKey));
                            }
                            monthlyFeedingCalendars.add(new Calendar(convertToMonthDate(key), ingredientRatios));
                        }
                        mListener.onCompletionHandler(true, GET_MONTHLY_FOOD, monthlyFeedingCalendars);
                        break;

                    case GET_SCHOOLS:
                        ArrayList<School> mSchools = new ArrayList<>();
                        String schoolString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONObject schoolObj = (JSONObject) new JSONTokener(schoolString).nextValue();
                        JSONArray schoolArray =  (JSONArray) schoolObj.get("schools");
                        for (int i = 0; i < schoolArray.length(); i++) {
                            JSONObject sch = schoolArray.getJSONObject(i);
                            School school = new School(sch.getString("name"),
                                    sch.getString("id"));
                            mSchools.add(school);
                        }
                        mListener.onCompletionHandler(true, GET_SCHOOLS, mSchools);
                        break;

                    case GET_CLASSES:
                        ArrayList<Class> mClasses = new ArrayList<>();
                        String classesString = new String(arrayOutputStream.toByteArray(), Charset.defaultCharset());
                        JSONObject classObj = (JSONObject) new JSONTokener(classesString).nextValue();
                        JSONArray classesArray =  (JSONArray) classObj.get("classes");
                        for (int i = 0; i < classesArray.length(); i++) {
                            JSONObject cl = classesArray.getJSONObject(i);
                            Class thisclass = new Class(cl.getString("name"),
                                    cl.getString("id"));
                            mClasses.add(thisclass);
                        }
                        mListener.onCompletionHandler(true, GET_CLASSES, mClasses);
                        break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
