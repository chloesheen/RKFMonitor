package com.example.app.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.app.R;

import java.util.HashMap;

import com.example.app.fragments.LunchProgramDialog;
import com.example.app.models.Food;
import com.example.app.util.FoodRatios;

import org.parceler.Parcels;

public class FeedingDashboard extends AppCompatActivity implements View.OnClickListener,
        LunchProgramDialog.OnLunchSelectionListener {

    private CardView mLunchCard;
    private CardView mBreakfastCard;
    private int mTotalAttendance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_dashboard);

        mLunchCard = (CardView) findViewById(R.id.lunch_card);
        mLunchCard.setOnClickListener(this);

        mBreakfastCard = (CardView) findViewById(R.id.breakfast_card);
        mBreakfastCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.breakfast_card:
                Food breakfast = FoodRatios.getUjiRatios(mTotalAttendance);
                Intent intent = new Intent(this, BreakfastProgramActivity.class);
                intent.putExtra("UjiMeal", Parcels.wrap(breakfast));
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

    public void onGitheriSelect() {
        Food githeri = FoodRatios.getGitheriRatios(mTotalAttendance);
        Intent intent = new Intent(this, GitheriProgramActivity.class);
        intent.putExtra("GitheriMeal", Parcels.wrap(githeri));
        startActivity(intent);
    }

    public void onRiceBeansSelect() {
        Food ricebeans = FoodRatios.getRiceBeansRatios(mTotalAttendance);
        Intent intent = new Intent(this, RiceBeansProgramActivity.class);
        intent.putExtra("RiceBeansMeal", Parcels.wrap(ricebeans));
        startActivity(intent);
    }

    @Override
    public void onLunchSelected (View view) {
        onClick(view);
    }
}
