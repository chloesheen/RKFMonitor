package com.example.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.app.R;
import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Student;
import com.example.app.util.BootingHandler;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.example.app.util.Constants.GET_STUDENTLIST_VIEW;
import static com.example.app.util.Constants.REQUEST_STUDENT_LIST;

public class MainActivity extends Activity implements CallbackListener {



    private BootingHandler mBootingHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS_KEY", MODE_PRIVATE);
        if (mSharedPreferences.getString("authorization", null) == null) {
            //Log.v("test1", mSharedPreferences.getString("authorization", null));
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            BootingHandler.initLauncher(this, this);
        }
         **/
        HttpGetRequests task = new HttpGetRequests(GET_STUDENTLIST_VIEW, this, this);
        task.execute(REQUEST_STUDENT_LIST);

        //Intent cookintent = new Intent(this, FeedingDashboard.class);
        //startActivity(cookintent);

    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object) {
        if (success) {
            switch (requestcode) {
                case GET_STUDENTLIST_VIEW:
                    ArrayList<Student> students = (ArrayList<Student>) object;
                    Intent intent = new Intent(this, ClassViewActivity.class);
                    intent.putExtra("Studentlist", Parcels.wrap(students));
                    startActivity(intent);
            }
        }
    }

}
