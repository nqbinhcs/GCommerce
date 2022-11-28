package com.example.e_commerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Adapter.CategoryAdapter;
import com.example.e_commerce.Adapter.PopularAdapter;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;
import com.example.e_commerce.R;
import com.example.e_commerce.Activity.CameraActivity;
import com.example.e_commerce.Detection.customview.OverlayView;
import com.example.e_commerce.Detection.env.ImageUtils;
import com.example.e_commerce.Detection.env.Logger;
import com.example.e_commerce.Detection.env.Utils;
import com.example.e_commerce.Detection.tflite.Classifier;
import com.example.e_commerce.Detection.tflite.YoloV5Classifier;
import com.example.e_commerce.Detection.tracking.MultiBoxTracker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView.Adapter categoryAdapter, popularAdapter;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    FloatingActionButton cameraButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerViewCategory();
        recyclerViewPopular();

        cameraButton = findViewById(R.id.cameraFloatBtn);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CameraActivity.class));
            }
        });

        bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cameraFloatBtn);
//        LinearLayout homeButtonn = findViewById(R.id.homeBtn);
//        LinearLayout profileButtonn = findViewById(R.id.profileBtn);
//        LinearLayout categoryButton = findViewById(R.id.categoryBtn);
//        LinearLayout cartButtonn = findViewById(R.id.cartBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CameraActivity.class));
            }
        });

//        profileBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//            }
//        });
//
//        friendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this,FriendActivity.class));
//            }
//        });
//
//        settingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this,SettingActivity.class));
//            }
//        });
    }

    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList=findViewById(R.id.categoryRecyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<Category> category = new ArrayList<>();

        // -------------------------Query from database-----------------------
        category.add(new Category("001","Fruits", "cat_11"));
        category.add(new Category("002","Vegetable", "cat_22"));
        category.add(new Category("003","Diary", "cat_33"));
        category.add(new Category("004","Meat", "cat_44"));

        categoryAdapter = new CategoryAdapter(category);
        recyclerViewCategoryList.setAdapter(categoryAdapter);
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.bestRecyclerView);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<Food> foodList = new ArrayList<>();

        // -------------------------Query from database-----------------------
        foodList.add(new Food("001", "Bell Peper Red", "red_pepper", "Binh", "Vegetable", "Like the tomato, bell peppers are botanical fruits but culinary vegetables. Pieces of bell pepper are commonly used in garden salads and as toppings on pizza", 34.0, 5.0, 1));
        foodList.add(new Food("002", "Lamb Meat", "lamb_meat", "Huy", "Meat", "A fresh meat", 45.0, 5.0, 1));
        foodList.add(new Food("003", "Combo Fresh", "fresh_fish_meat", "Son", "Meat","Meat and Fish", 90.0, 5.0, 1));

        popularAdapter = new PopularAdapter(foodList);
        recyclerViewPopularList.setAdapter(popularAdapter);

    }
}