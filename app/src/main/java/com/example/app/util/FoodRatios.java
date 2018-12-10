package com.example.app.util;

import android.content.Context;

import com.example.app.models.Food;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FoodRatios {

    public static Food mUji;
    public static Food mGitheri;
    public static Food mRiceBeans;

    /**
     * Creates and initializes the Feeding meals
     */
    public static void createFoodObjects() {
        HashMap<String, String> breakfastIngredients = new HashMap<>();
        breakfastIngredients.put("Flour", "0.00 kg");
        breakfastIngredients.put("Sugar", "0.00 kg");
        breakfastIngredients.put("Water", "0.00");
        mUji  = new Food("Uji", breakfastIngredients);

        HashMap<String, String> githeriIngredients = new HashMap<>();
        githeriIngredients.put("Maize", "0.00 kg");
        githeriIngredients.put("Beans", "0.00 kg");
        githeriIngredients.put("Salt", "0.00 kg");
        githeriIngredients.put("Oil", "0.00 ltrs");
        mGitheri = new Food("Githeri", githeriIngredients);

        HashMap<String, String> rice_beansIngredients = new HashMap<>();
        rice_beansIngredients.put("Rice", "0.00 kg");
        rice_beansIngredients.put("Beans", "0.00 kg");
        rice_beansIngredients.put("Salt", "0.00 kg");
        rice_beansIngredients.put("Oil", "0.00 ltrs");
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
        HashMap<String, String> ingredients = mUji.getRatios();
        double flourQuantity = attendance/15.00;
        String currFlour = doubleFormatter(flourQuantity);
        String flourratio = ingredients.get("Flour");
        ingredients.put("Flour", flourratio.replace(flourratio.substring(0, 4), currFlour));

        String sugarQuantity = doubleFormatter(flourQuantity/4.00);
        String currSugar = ingredients.get("Sugar");
        ingredients.put("Sugar", currSugar.replace(currSugar.substring(0, 4), sugarQuantity));

        String waterQuantity = doubleFormatter(flourQuantity/4.00);
        ingredients.put("Water", waterQuantity);
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
        HashMap<String, String> ingredients = mGitheri.getRatios();
        double maizeQuantity = attendance / 9.00;
        String currMaize = doubleFormatter(maizeQuantity);
        String maizeratio = ingredients.get("Maize");
        ingredients.put("Maize", maizeratio.replace(maizeratio.substring(0, 4), currMaize));

        String beansQuantity = doubleFormatter(maizeQuantity/2.00);
        String currBeans = ingredients.get("Beans");
        ingredients.put("Beans", currBeans.replace(currBeans.substring(0, 4), beansQuantity));

        String saltQuantity = doubleFormatter(attendance/400.00);
        String currSalt = ingredients.get("Salt");
        ingredients.put("Salt", currSalt.replace(currSalt.substring(0, 4), saltQuantity));

        String oilQuantity = doubleFormatter(attendance/250.00);
        String currOil = ingredients.get("Oil");
        ingredients.put("Oil", currOil.replace(currOil.substring(0, 4), oilQuantity));

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
        HashMap<String, String> ingredients = mRiceBeans.getRatios();
        double riceQuantity = attendance / 12.00;
        String currRice = doubleFormatter(riceQuantity);
        String riceratio = ingredients.get("Rice");
        ingredients.put("Rice", riceratio.replace(riceratio.substring(0, 4), currRice));

        String beansQuantity = doubleFormatter(riceQuantity/2.00);
        String currBeans = ingredients.get("Beans");
        ingredients.put("Beans", currBeans.replace(currBeans.substring(0, 4), beansQuantity));

        String saltQuantity = doubleFormatter(attendance/400.00);
        String currSalt = ingredients.get("Salt");
        ingredients.put("Salt", currSalt.replace(currSalt.substring(0, 4), saltQuantity));

        String oilQuantity = doubleFormatter(attendance/250.00);
        String currOil = ingredients.get("Oil");
        ingredients.put("Oil", currOil.replace(currOil.substring(0, 4), oilQuantity));

        return mRiceBeans;
    }

    public static ArrayList<Pair> populateList(Food food) {
        ArrayList<Pair> mRatioPairs = new ArrayList<>();
        HashMap<String, String> ratios = food.getRatios();
        Iterator iterator = ratios.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Pair ratiopair = new Pair(pair.getKey(), pair.getValue());
            mRatioPairs.add(ratiopair);
        }
        return mRatioPairs;
    }

    public static String doubleFormatter(Double d) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(d);
    }

}

