package com.example.app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.util.Pair;

import java.util.ArrayList;

public class FoodRatioAdapter extends ArrayAdapter<Pair> {

    private ArrayList<Pair> mRatioList;

    public FoodRatioAdapter (@NonNull Context context, ArrayList<Pair> ratios) {
        super(context, 0, ratios);
        mRatioList = ratios;
    }

    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        final Pair<String, String> ratio = mRatioList.get(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.food_ratio_item, parent, false);
        }

        TextView ingredientName = (TextView) view.findViewById(R.id.ingredient_name);
        TextView ingredientAmount = (TextView) view.findViewById(R.id.ingredient_quantity);
        ingredientName.setText(ratio.getFirst());
        ingredientAmount.setText(ratio.getSecond());
        return view;
    }
}
