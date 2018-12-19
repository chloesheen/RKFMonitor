package com.example.app.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.app.activities.FeedingDashboard;
import com.example.app.activities.LoginActivity;
import com.example.app.activities.OrganizationDashboard;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.interfaces.CallbackListener;

import static android.content.Context.MODE_PRIVATE;
import static com.example.app.util.Constants.GET_STUDENTLIST_VIEW;
import static com.example.app.util.Constants.REQUEST_STUDENT_LIST;

public class BootingHandler {

    private SharedPreferences mSharedPreferences;

    public BootingHandler(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public void initLauncher(Context context, CallbackListener listener) {
         mSharedPreferences = context.getSharedPreferences("SHARED_PREFS_KEY", MODE_PRIVATE);
        if (mSharedPreferences.getString("isAdministrator", null).equals("true")) {
            Intent orgIntent = new Intent(context, OrganizationDashboard.class);
            context.startActivity(orgIntent);
        } else if (mSharedPreferences.getString("isTeacher", null).equals("true")){
            HttpGetRequests task = new HttpGetRequests(GET_STUDENTLIST_VIEW, listener, context);
            task.execute(REQUEST_STUDENT_LIST);
        } else if (mSharedPreferences.getString("isCook", null).equals("true")) {
            Intent cookIntent = new Intent(context, FeedingDashboard.class);
            context.startActivity(cookIntent);
        }

    }


}
