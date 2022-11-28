package com.example.e_commerce.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Adaptor.CategoryAdaptor;
import com.example.e_commerce.Adaptor.PopularAdaptor;
import com.example.e_commerce.Domain.CategoryDomain;
import com.example.e_commerce.Domain.FoodDomain;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter categoryAdapter, popularAdapter;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopular();
    }

    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList=findViewById(R.id.categoryRecyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();

        // -------------------------Query from database-----------------------
        category.add(new CategoryDomain("001","Fruits", "cat_11"));
        category.add(new CategoryDomain("002","Vegetable", "cat_22"));
        category.add(new CategoryDomain("003","Diary", "cat_33"));
        category.add(new CategoryDomain("004","Meat", "cat_44"));

        categoryAdapter = new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(categoryAdapter);
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.bestRecyclerView);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList = new ArrayList<>();

        // -------------------------Query from database-----------------------
        foodList.add(new FoodDomain("001", "Bell Peper Red", "red_pepper", "Binh", "Vegetable", "Like the tomato, bell peppers are botanical fruits but culinary vegetables. Pieces of bell pepper are commonly used in garden salads and as toppings on pizza", 34.0, 5.0, 1));
        foodList.add(new FoodDomain("002", "Lamb Meat", "lamb_meat", "Huy", "Meat", "A fresh meat", 45.0, 5.0, 1));
        foodList.add(new FoodDomain("003", "Combo Fresh", "fresh_fish_meat", "Son", "Meat","Meat and Fish", 90.0, 5.0, 1));

        popularAdapter = new PopularAdaptor(foodList);
        recyclerViewPopularList.setAdapter(popularAdapter);

    }
}