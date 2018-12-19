package com.example.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Student;
import com.example.app.util.BootingHandler;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.example.app.util.Constants.GET_STUDENTLIST_VIEW;

public class MainActivity extends Activity implements CallbackListener {

    private BootingHandler mBootingHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS_KEY", MODE_PRIVATE);
        mBootingHandler = new BootingHandler(mSharedPreferences);
        if (mSharedPreferences.getString("authorization", null) == null) {
            //Log.v("test1", mSharedPreferences.getString("authorization", null));
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            mBootingHandler.initLauncher(this, this);
        }
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
