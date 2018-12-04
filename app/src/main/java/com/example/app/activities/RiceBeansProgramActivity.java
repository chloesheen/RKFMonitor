package com.example.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapters.FoodRatioAdapter;
import com.example.app.models.Food;
import com.example.app.util.Pair;

import org.parceler.Parcels;

import java.util.ArrayList;

import static com.example.app.util.DateUtils.setDate;
import static com.example.app.util.FoodRatios.populateList;

public class RiceBeansProgramActivity extends AppCompatActivity {

    private ArrayList<Pair> mRatioPairs = new ArrayList<>();
    private Food mRiceBeans;
    private TextView mCurentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice_beans_program);
        mRiceBeans = Parcels.unwrap(getIntent().getParcelableExtra("RiceBeansMeal"));
        mRatioPairs = populateList(mRiceBeans);
        mCurentDate = (TextView) findViewById(R.id. riceactivity_date);
        mCurentDate.setText(setDate());
        ListView ratioList = (ListView) findViewById(R.id.ricelist);
        FoodRatioAdapter adapter = new FoodRatioAdapter(this, mRatioPairs);
        ratioList.setAdapter(adapter);
    }
}
