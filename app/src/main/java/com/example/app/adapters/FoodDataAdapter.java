package com.example.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.models.Food;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class FoodDataAdapter extends ArrayAdapter<Food> {

    private ArrayList<Food> mFoodList;

    public FoodDataAdapter(Context context, int resource, ArrayList<Food> foodlist) {
        super(context, resource, foodlist);
        mFoodList = foodlist;
    }

    @Override
    public View getView(int position, View view, ViewGroup group) {
        final Food meal = mFoodList.get(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.food_item, group, false);
        }

        TableLayout mealTable = view.findViewById(R.id.singleMealRatios);
        TextView mealname = view.findViewById(R.id.mealHeading);
        mealname.setText(meal.getFoodName());
        Iterator iterator = meal.getRatios().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mealratio = (Map.Entry)iterator.next();
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView ingredient = new TextView(getContext());
            ingredient.setText(mealratio.getKey().toString());
            ingredient.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(ingredient);

            TextView ingredientRatio = new TextView(getContext());
            ingredientRatio.setText(mealratio.getValue().toString());
            ingredientRatio.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(ingredientRatio);
            
            mealTable.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }
        return view;
    }
}
