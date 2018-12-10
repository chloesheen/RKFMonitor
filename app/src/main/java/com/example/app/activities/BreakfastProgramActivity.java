package com.example.app.activities;

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
import static com.example.app.util.DateUtils.setDate;
import static com.example.app.util.FoodRatios.populateList;

public class BreakfastProgramActivity extends AppCompatActivity implements CallbackListener {

    private ArrayList<Pair> mRatioPairs = new ArrayList<>();
    private Food mUji;
    private TextView mCurrentDate;



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
        task.execute("");
    }

    @Override
    public void onCompletionHandler(boolean success, int requestcode, Object object){}

}
