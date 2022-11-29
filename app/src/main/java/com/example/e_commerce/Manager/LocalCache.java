package com.example.e_commerce.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.e_commerce.Model.User;
import com.google.gson.Gson;

public class LocalCache {
    SharedPreferences preferences;

    public LocalCache(Context context, String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void addUser(User user) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        Gson gson = new Gson();
        preferencesEditor.putString("User", gson.toJson(user));
        preferencesEditor.commit();
    }

    public User loadUser() {
        if (preferences.contains("User")) {
            Gson gson = new Gson();
            return gson.fromJson(preferences.getString("User", ""), User.class);
        }
        return null;
    }
}
