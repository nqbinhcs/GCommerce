package com.example.e_commerce.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Adapter.FoodAdapter;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FoodByCategoryActivity extends AppCompatActivity  {

    public RecyclerView recyclerView;
    public FoodAdapter adapter;
    private String catTitle;
    private String catID;
    private TextView catName;
    private ImageView backButton;

    public ArrayList<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_by_category);
        initView();
        getBundle();
        recyclerViewFoodCat();
        bottomNavigation();
        addEventBackButton();
    }

    private void addEventBackButton() {
        backButton = (ImageView) findViewById(R.id.back_arrow_show_detail);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        catName = findViewById(R.id.catNameTxT);
    }


    private void getBundle() {
        catTitle = (String) getIntent().getStringExtra("category");
        catID = (String) getIntent().getStringExtra("categoryID");
        catName.setText(catTitle);
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cameraFloatBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout categoryBtn = findViewById(R.id.categoryBtn);
        LinearLayout bagBtn = findViewById(R.id.cartBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);

//        homeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(FoodByCategoryActivity.this, MainActivity.class));
//            }
//        });
//
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FoodByCategoryActivity.this,SearchCameraActivity.class));
//            }
//        });
//
//        profileBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FoodByCategoryActivity.this, ProfileActivity.class));
//            }
//        });
//
//        bagBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FoodByCategoryActivity.this,CartActivity.class));
//            }
//        });

//        settingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FoodByCategoryActivity.this,SettingActivity.class));
//            }
//        });
    }


    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodCat);
        recyclerView.setLayoutManager(linearLayoutManager);

        foodList = new ArrayList<Food>();
        // -------------------------Query from database-----------------------
        foodList.add(new Food("001", "Bell Peper Red", "red_pepper", "Binh", "Vegetable", "Pieces of bell pepper are commonly used in garden salads and as toppings on pizza", 34.0, 5.0, 1));
        adapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(adapter);

    }


}