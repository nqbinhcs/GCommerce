package com.example.e_commerce.Listener;

import com.example.e_commerce.Model.RecipeDetailsResponse;


public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response,String message);
    void didError(String message);
}