package com.example.app.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.FoodRatioAdapter;
import com.example.app.asynctasks.HttpPostRequests;
import com.example.app.interfaces.CallbackListener;
import com.example.app.models.Food;
import com.example.app.util.FoodRatios;
import com.example.app.util.Pair;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.example.app.util.Constants.POST_FOOD;
import static com.example.app.util.Constants.REQUEST_ADD_FOOD;
import static com.example.app.util.Constants.SHARED_PREFS_KEY;
import static com.example.app.util.DateUtils.setDate;
import static com.example.app.util.FoodRatios.populateList;

public class BreakfastProgramActivity extends AppCompatActivity implements CallbackListener {

    private ArrayList<Pair> mRatioPairs = new ArrayList<>();
    private Food mUji;
    private TextView mCurrentDate;

    //Info we need from shared preferences, teacher log in name, class name,
    private SharedPreferences mSharedPreferences = this.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_program);
        mUji = Parcels.unwrap(getIntent().getParcelableExtra("UjiMeal"));
        mRatioPairs = populateList(mUji);
        mCurrentDate = (TextView) findViewById(R.id.bfactivity_date);
        mCurrentDate.setText(setDate());
        ListView ratiosList = (ListView) findViewById(R.id.ujilist);
        FoodRatioAdapter pairArrayAdapter = new FoodRatioAdapter(this, mRatioPairs);
        ratiosList.setAdapter(pairArrayAdapter);

        TextView mProfilename = (TextView) findViewById(R.id.profile_name);
        mProfilename.setText(mSharedPreferences.getString("username", null));

        TextView schoolname = (TextView) findViewById(R.id.bfactivity_schoolname);
        schoolname.setText(mSharedPreferences.getString("school", null));
    }

    @Override
    public void onStart() {
        super.onStart();
        HashMap<String, String> ratios = mUji.getRatios();
        HashMap<String, String> foodInfo = new HashMap<>();
        foodInfo.put("Date", mCurrentDate.getText().toString());
        foodInfo.put("MealType", "Breakfast");
        for (Map.Entry<String, String> ratio : ratios.entrySet()) {
            foodInfo.put(ratio.getKey(), ratio.getValue());
        }
        HttpPostRequests task = new HttpPostRequests(foodInfo, POST_FOOD, this, this);
        task.execute(REQUEST_ADD_FOOD);
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object){}

}
