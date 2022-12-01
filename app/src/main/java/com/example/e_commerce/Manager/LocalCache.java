package com.example.e_commerce.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.e_commerce.Model.Food;
import com.example.e_commerce.Model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LocalCache {
    SharedPreferences preferences;

    public LocalCache(Context context, String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void addUser(User user) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        Gson gson = new Gson();
        preferencesEditor.putString("User", gson.toJson(user));
        preferencesEditor.apply();
    }

    public User loadUser() {
        if (preferences.contains("User")) {
            Gson gson = new Gson();
            return gson.fromJson(preferences.getString("User", ""), User.class);
        }
        return null;
    }

    public void deleteUser() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.remove("User");
        preferencesEditor.apply();
    }

    public void addFood(Food food) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        Gson gson = new Gson();
        preferencesEditor.putString("Food", gson.toJson(food));
        preferencesEditor.apply();
    }

    public Food loadFood() {
        if (preferences.contains("Food")) {
            Gson gson = new Gson();
            return gson.fromJson(preferences.getString("Food", ""), Food.class);
        }
        return null;
    }

    public void deleteFood() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.remove("Food");
        preferencesEditor.apply();
    }


    public void addFoodList(ArrayList<Food> foodList) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        Gson gson = new Gson();
        ArrayList<String> stringList = new ArrayList<>();
        for (Food food : foodList)
            stringList.add(gson.toJson(food));
        preferencesEditor.putString("FoodList", gson.toJson(stringList));
        preferencesEditor.apply();
    }

    public ArrayList<Food> loadFoodList() {
        if (preferences.contains("FoodList")) {
            Gson gson = new Gson();
            ArrayList<String> stringList = gson.fromJson(preferences.getString("FoodList", ""), ArrayList.class);
            ArrayList<Food> foodList = new ArrayList<>();
            for (String string : stringList) {
                foodList.add(gson.fromJson(string, Food.class));
            }
            return foodList;
        }
        return null;
    }

    public void deleteFoodList() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.remove("FoodList");
        preferencesEditor.apply();
    }
}
