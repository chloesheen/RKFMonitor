package com.example.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.adapters.FoodDataAdapter;
import com.example.app.models.Food;

import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FeedingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding);
        ArrayList<Food> meals = Parcels.unwrap(getIntent().getParcelableExtra("selectedMeals"));

        ListView foodlist = (ListView) findViewById(R.id.foodList);
        FoodDataAdapter adapter = new FoodDataAdapter(this, 0, meals);
        foodlist.setAdapter(adapter);
    }
}
