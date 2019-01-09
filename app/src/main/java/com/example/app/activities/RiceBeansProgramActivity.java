package com.example.app.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.FoodRatioAdapter;
import com.example.app.asynctasks.HttpPostRequests;
import com.example.app.asynctasks.HttpPutRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Food;
import com.example.app.util.Pair;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.app.util.Constants.POST_FOOD;
import static com.example.app.util.Constants.PUT_FOOD;
import static com.example.app.util.Constants.REQUEST_ADD_FOOD;
import static com.example.app.util.Constants.SHARED_PREFS_KEY;
import static com.example.app.util.DateUtils.setDate;
import static com.example.app.util.FoodRatios.populateList;

public class RiceBeansProgramActivity extends AppCompatActivity implements CallbackListener {

    private ArrayList<Pair> mRatioPairs = new ArrayList<>();
    private Food mRiceBeans;
    private TextView mCurentDate;
    private TextView mCurrentAttendance;
    //private SharedPreferences mSharedPreferences = this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice_beans_program);
        mRiceBeans = Parcels.unwrap(getIntent().getParcelableExtra("RiceBeansMeal"));
        int attendancenum = getIntent().getIntExtra("attendancenum", 0);
        mCurrentAttendance = (TextView) findViewById(R.id.ricebeans_attendance);
        mCurrentAttendance.setText(String.valueOf(attendancenum));

        TextView mProfilename = (TextView) findViewById(R.id.profile_name);
        //mProfilename.setText(mSharedPreferences.getString("username", null));

        TextView schoolname = (TextView) findViewById(R.id.riceactivity_schoolname);
        //schoolname.setText(mSharedPreferences.getString("school", null));

        mRatioPairs = populateList(mRiceBeans);
        mCurentDate = (TextView) findViewById(R.id. riceactivity_date);
        mCurentDate.setText(setDate());
        ListView ratioList = (ListView) findViewById(R.id.ricelist);
        FoodRatioAdapter adapter = new FoodRatioAdapter(this, mRatioPairs);
        ratioList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        HashMap<String, String> ratios = mRiceBeans.getRatios();
        HashMap<String, Object> foodInfo = new HashMap<>();
        foodInfo.put("MealType", "RB");
        for (Map.Entry<String, String> ratio : ratios.entrySet()) {
            String val = ratio.getValue();
            foodInfo.put(ratio.getKey(), Double.valueOf(val.substring(0, 4)));
        }
        HttpPutRequests task = new HttpPutRequests(foodInfo, PUT_FOOD, this, this);
        task.execute(REQUEST_ADD_FOOD);
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object){}
}
