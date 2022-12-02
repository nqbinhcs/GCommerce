package com.example.e_commerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class DetailOrderActivity extends AppCompatActivity {
    private LocalCache localCache;
    private ImageView backArrow;
    private ArrayList<Food> foodList;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailorder);
        initView();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailOrderActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localCache.deleteFoodList();
                Intent intent = new Intent(DetailOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        backArrow = (ImageView) findViewById(R.id.back_arrow_detail_order);
        localCache = new LocalCache(this, "Local cache");
        foodList = localCache.loadFoodList();
        cancelBtn = (Button) findViewById(R.id.cancelbtn);
    }

}
