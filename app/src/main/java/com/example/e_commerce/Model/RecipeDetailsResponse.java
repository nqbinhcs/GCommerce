package com.example.e_commerce.Model;

import java.util.ArrayList;

public class RecipeDetailsResponse {
    public int id;
    public String title;
    public String image;
    public String imageType;
    public int servings;
    public int readyInMinutes;
    public String license;
    public String sourceName;
    public String sourceUrl;
    public String spoonacularSourceUrl;
    public int aggregateLikes;
    public double healthScore;
    public double spoonacularScore;
    public double pricePerServing;
    public ArrayList<Object> analyzedInstructions;
    public boolean cheap;
    public String creditsText;
    public ArrayList<Object> cuisines;
    public boolean dairyFree;
    public ArrayList<Object> diets;
    public String gaps;
    public boolean glutenFree;
    public String instructions;
    public boolean ketogenic;
    public boolean lowFodmap;
    public ArrayList<Object> occasions;
    public boolean sustainable;
    public boolean vegan;
    public boolean vegetarian;
    public boolean veryHealthy;
    public boolean veryPopular;
    public boolean whole30;
    public int weightWatcherSmartPoints;
    public ArrayList<String> dishTypes;
    public ArrayList<ExtendedIngredient> extendedIngredients;
    public String summary;
}
