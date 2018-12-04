package com.example.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.adapters.FoodRatioAdapter;
import com.example.app.models.Food;
import com.example.app.util.Pair;

import org.parceler.Parcels;

import java.util.ArrayList;

public class BreakfastProgramActivity extends AppCompatActivity {

    ArrayList<Pair> mRatioPairs;
    Food mUji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_program);
        mUji = Parcels.unwrap(getIntent().getParcelableExtra("UjiMeal"));
        ListView ratiosList = (ListView) findViewById(R.id.ujilist);
        FoodRatioAdapter pairArrayAdapter = new FoodRatioAdapter();
        ratiosList.setAdapter(pairArrayAdapter);
    }
}
