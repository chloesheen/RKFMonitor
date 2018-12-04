package com.example.app.util;

import android.content.Context;

import com.example.app.models.Food;

import java.util.HashMap;

public class FoodRatios {

    public static Food mUji;
    public static Food mGitheri;
    public static Food mRiceBeans;

    /**
     * Creates and initializes the Feeding meals
     */
    public static void createFoodObjects() {
        HashMap<String, Integer> breakfastIngredients = new HashMap<>();
        breakfastIngredients.put("Flour", 0);
        breakfastIngredients.put("Sugar", 0);
        breakfastIngredients.put("Water", 0);
        mUji  = new Food("Uji", breakfastIngredients);

        HashMap<String, Integer> githeriIngredients = new HashMap<>();
        githeriIngredients.put("Maize", 0);
        githeriIngredients.put("Beans", 0);
        githeriIngredients.put("Salt", 0);
        githeriIngredients.put("Oil", 0);
        mGitheri = new Food("Githeri", githeriIngredients);

        HashMap<String, Integer> rice_beansIngredients = new HashMap<>();
        rice_beansIngredients.put("Rice", 0);
        rice_beansIngredients.put("Beans", 0);
        rice_beansIngredients.put("Salt", 0);
        rice_beansIngredients.put("Oil", 0);
        mRiceBeans = new Food("Rice&Beans", rice_beansIngredients);
    }

    /**
     * Calculates the quantities of ingredients for the Uji meal
     *
     * @param attendance; total atendance of students
     * @return the updated Uji food object
     */

    public static Food getUjiRatios(int attendance) {
        createFoodObjects();
        HashMap<String, Integer> ingredients = mUji.getRatios();
        ingredients.put("Flour", attendance / 15);
        int flourQuantity = (int) ingredients.get("Flour");
        ingredients.put("Sugar", flourQuantity / 4);
        ingredients.put("Water", flourQuantity * 2);
        return mUji;
    }

    /**
     * Calculates the quantities for Githeri
     *
     * @param attendance total atendance of students
     * @return the updated Githeri food object
     */
    public static Food getGitheriRatios(int attendance) {
        createFoodObjects();
        HashMap<String, Integer> ingredients = mGitheri.getRatios();
        int maizeQuantity = attendance / 9;
        ingredients.put("Maize", maizeQuantity);
        ingredients.put("Beans", maizeQuantity / 2);
        ingredients.put("Salt", attendance / 400);
        ingredients.put("Oil", attendance / 250);
        return mGitheri;
    }

    /**
     * Calculates the quantities for Githeri
     *
     * @param attendance total atendance of students
     * @return the updated Githeri food object
     */
    public static Food getRiceBeansRatios(int attendance) {
        createFoodObjects();
        HashMap<String, Integer> ingredients = mRiceBeans.getRatios();
        int riceQuantity = attendance / 12;
        ingredients.put("Rice", riceQuantity);
        ingredients.put("Beans", riceQuantity / 2);
        ingredients.put("Salt", attendance / 400);
        ingredients.put("Oil", attendance / 250);
        return mRiceBeans;


    }

}

