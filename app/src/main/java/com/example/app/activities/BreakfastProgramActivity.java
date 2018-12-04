package com.example.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.FoodRatioAdapter;
import com.example.app.models.Food;
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

public class BreakfastProgramActivity extends AppCompatActivity {

    private ArrayList<Pair> mRatioPairs = new ArrayList<>();
    private Food mUji;
    private TextView mCurrentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_program);
        mUji = Parcels.unwrap(getIntent().getParcelableExtra("UjiMeal"));
        populateList();
        mCurrentDate = (TextView) findViewById(R.id.bfactivity_date);
        mCurrentDate.setText(setDate());
        ListView ratiosList = (ListView) findViewById(R.id.ujilist);
        FoodRatioAdapter pairArrayAdapter = new FoodRatioAdapter(this, mRatioPairs);
        ratiosList.setAdapter(pairArrayAdapter);
    }

    private void populateList() {
        HashMap<String, Integer> ratios = mUji.getRatios();
        Iterator iterator = ratios.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Pair ratiopair = new Pair(pair.getKey(), pair.getValue());
            mRatioPairs.add(ratiopair);
        }
    }

    private String setDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMMM dd YYYY hh:mm", Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        return dateFormatter.format(currentDate);

    }
}
