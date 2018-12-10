package com.example.app.models;

import java.util.HashMap;

@org.parceler.Parcel
public class Food {

    private String mFoodName;
    private HashMap<String, String> mIngredientRatios;

    public Food() {

    }

    public Food(String name, HashMap<String, String> ingredientRations) {
        mFoodName = name;
        mIngredientRatios = ingredientRations;
    }

    public String getFoodName() {
        return mFoodName;
    }

    public HashMap getRatios() {
        return mIngredientRatios;
    }
}
