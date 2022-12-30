package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.e_commerce.API.RequestManager;
import com.example.e_commerce.Adapter.IngredientsAdapter;
import com.example.e_commerce.Adapter.InstructionsAdapter;
import com.example.e_commerce.Adapter.SimilarListAdapter;
import com.example.e_commerce.Listener.InstructionsListener;
import com.example.e_commerce.Listener.RecipeClickListener;
import com.example.e_commerce.Listener.RecipeDetailsListener;
import com.example.e_commerce.Listener.SimilarRecipeListener;
import com.example.e_commerce.Model.ExtendedIngredient;
import com.example.e_commerce.Model.InstructionsResponse;
import com.example.e_commerce.Model.RecipeDetailsResponse;
import com.example.e_commerce.Model.SimilarRecipeResponse;
import com.example.e_commerce.R;
//import com.github.ybq.android.spinkit.sprite.Sprite;
//import com.github.ybq.android.spinkit.style.Wave;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    TextView textView_meal_name, textView_meal_source;
    TextView textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients, recycler_meal_similar, recycler_meal_instructions;
    Button button_nutrition;
    ScrollView scrollView;

    RequestManager manager;
    IngredientsAdapter adapter;
    SimilarListAdapter similarListAdapter;
    InstructionsAdapter instructionsAdapter;
    ProgressDialog dialog;
    //    ProgressDialog dialog;
//    ProgressBar progressBar;
    List<ExtendedIngredient> ingredientList;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();

//        dialog = new ProgressDialog(this);
//        dialog.setTitle("Please wait...");
//        progressBar = (ProgressBar)findViewById(R.id.loader);
//        Sprite doubleBounce = new Wave();
//        progressBar.setIndeterminateDrawable(doubleBounce);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");

        id = Integer.valueOf(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(listener, id);

        manager.getSimilarRecipe(similarRecipeListener, id);
        manager.getInstructions(instructionsListener, id);

        dialog.show();

        button_nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://api.spoonacular.com/recipes/1082038/nutritionWidget.png
//                https://api.spoonacular.com/recipes/641166/nutritionLabel.png

                final Dialog nutritionDialog = new Dialog(RecipeDetailsActivity.this, android.R.style.Theme_Light);
                nutritionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nutritionDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                nutritionDialog.setContentView(R.layout.nutrition_dialog);
                ImageView imageView = (ImageView) nutritionDialog.findViewById(R.id.imageView_nutrition);
                Picasso.get().load("https://api.spoonacular.com/recipes/"+id+"/nutritionLabel.png?apiKey="+getString(R.string.spoonacular_api_key)).into(imageView);
                nutritionDialog.show();
            }
        });

    }

    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);

        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);
        button_nutrition = findViewById(R.id.button_nutrition);
        scrollView = findViewById(R.id.scrollView);

    }

    private final RecipeDetailsListener listener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            showData(response);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(RecipeDetailsResponse response) {
        textView_meal_name.setText(response.title);
        textView_meal_source.setText(response.sourceName);
        Picasso.get().load(response.image).into(imageView_meal_image);

//        textView_meal_price.setText(response.pricePerServing + " $ per serving");
        textView_meal_summary.setText(response.summary);
//        dialog.dismiss();
//        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        recycler_meal_ingredients.setHasFixedSize(true);
        recycler_meal_ingredients.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        adapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
        recycler_meal_ingredients.setAdapter(adapter);
    }

    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
            similarListAdapter = new SimilarListAdapter(RecipeDetailsActivity.this, response, similarOnClickListener);
            recycler_meal_similar.setAdapter(similarListAdapter);
//            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void didError(String message) {

        }
    };

    private final RecipeClickListener similarOnClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String text) {
//            Toast.makeText(RecipeDetailActivity.this, text, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", text));
        }
    };

    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> responses, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
            instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, responses);
            recycler_meal_instructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}