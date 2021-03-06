package com.example.app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.app.R;

import java.util.HashMap;

import com.example.app.asynctasks.HttpGetRequests;
import com.example.app.fragments.LunchProgramDialog;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Food;
import com.example.app.util.FoodRatios;

import org.parceler.Parcels;

import static com.example.app.util.Constants.GET_TEACHER_PROFILE;
import static com.example.app.util.Constants.GET_TOTAL_ATTENDANCE;
import static com.example.app.util.Constants.REQUEST_COOK_DASHBOARD;
import static com.example.app.util.Constants.REQUEST_TEACHER_PROFILE;
import static com.example.app.util.Constants.SHARED_PREFS_KEY;
import static com.example.app.util.DateUtils.setDate;

public class FeedingDashboard extends AppCompatActivity implements View.OnClickListener,
        LunchProgramDialog.OnLunchSelectionListener, CallbackListener {

    private int mTotalAttendance;
    private TextView mDisplayAttendance;

    private CallbackListener mListener;
    private Context mContext;

    //private SharedPreferences mSharedPreferences = this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListener = this;
        mContext = this;

        TextView schoolname = (TextView) findViewById(R.id.cookdashboard_schoolname);
        //schoolname.setText(mSharedPreferences.getString("school", ""));
        TextView profilename = (TextView) findViewById(R.id.profile_name);
        //profilename.setText(mSharedPreferences.getString("username", ""));

        CardView mLunchCard = (CardView) findViewById(R.id.lunch_card);
        mLunchCard.setOnClickListener(this);

        CardView mBreakfastCard = (CardView) findViewById(R.id.breakfast_card);
        mBreakfastCard.setOnClickListener(this);

        TextView mDate = (TextView) findViewById(R.id.dashboard_date);
        mDate.setText(setDate());
        HttpGetRequests task = new HttpGetRequests(GET_TOTAL_ATTENDANCE, this, this);
        task.execute(REQUEST_COOK_DASHBOARD);
    }

    @Override
    public void onStart() {
        super.onStart();
        mDisplayAttendance = (TextView) findViewById(R.id.attendance_num);
        mDisplayAttendance.setText(String.valueOf(mTotalAttendance));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            HttpGetRequests gettask = new HttpGetRequests(GET_TEACHER_PROFILE, mListener, mContext);
            Log.v("profile", "testing teacher profile");
            gettask.execute(REQUEST_TEACHER_PROFILE);
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.breakfast_card:
                Food breakfast = FoodRatios.getUjiRatios(mTotalAttendance);
                Intent intent = new Intent(this, BreakfastProgramActivity.class);
                intent.putExtra("UjiMeal", Parcels.wrap(breakfast));
                intent.putExtra("attendancenum", mTotalAttendance);
                startActivity(intent);
                break;

            case R.id.lunch_card:
                FragmentManager manager = getSupportFragmentManager();
                LunchProgramDialog dialog = new LunchProgramDialog();
                dialog.show(manager, "lunchselectiondialog");
                break;

            case R.id.githeri:
                onGitheriSelect();
                break;

            case R.id.rice_beans:
                onRiceBeansSelect();
                break;
        }
    }


    /**
     * Might have to do these on a different thread. Monitor frames skipped
     */

    public void onGitheriSelect() {
        Food githeri = FoodRatios.getGitheriRatios(mTotalAttendance);
        Intent intent = new Intent(this, GitheriProgramActivity.class);
        intent.putExtra("GitheriMeal", Parcels.wrap(githeri));
        intent.putExtra("attendancenum", mTotalAttendance);
        startActivity(intent);
    }

    public void onRiceBeansSelect() {
        Food ricebeans = FoodRatios.getRiceBeansRatios(mTotalAttendance);
        Intent intent = new Intent(this, RiceBeansProgramActivity.class);
        intent.putExtra("RiceBeansMeal", Parcels.wrap(ricebeans));
        intent.putExtra("attendancenum", mTotalAttendance);
        startActivity(intent);
    }

    @Override
    public void onLunchSelected (View view) {
        onClick(view);
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object) {
        if (success && requestcode == GET_TOTAL_ATTENDANCE) {
            mTotalAttendance = (int) object;
            Log.v("totalattendance", String.valueOf(mTotalAttendance));
            //???

        }
    }
}
