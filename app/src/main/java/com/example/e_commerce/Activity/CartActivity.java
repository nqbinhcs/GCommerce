package com.example.e_commerce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.e_commerce.Adapter.FoodAdapter;
import com.example.e_commerce.Adapter.FoodCartAdapter;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public FoodCartAdapter adapter;
    private ScrollView scrollView;
    private TextView noFoundItem;
    private TextView subTotalView, disCountView, deliveryView, totalView;
    ArrayList<Food> foodList;
    LocalCache localCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodCat);
        recyclerView.setLayoutManager(linearLayoutManager);
        scrollView = (ScrollView) findViewById(R.id.scrollView6);
        noFoundItem = (TextView) findViewById(R.id.noitemfound);
        localCache = new LocalCache(this, "Local cache");
        foodList = localCache.loadFoodList();

        subTotalView = (TextView) findViewById(R.id.subtotal);
        disCountView = (TextView) findViewById(R.id.discount);
        deliveryView = (TextView) findViewById(R.id.deliverycharge);
        totalView = (TextView) findViewById(R.id.total);


        double subTotal = 0;
        double disCount = 0;
        double deliveryCharge = 15;

        if (foodList.size() > 0) {
            for (Food food : foodList) {
                subTotal += food.getCost() * food.getNumberInCart();
            }
            adapter = new FoodCartAdapter(foodList);
            recyclerView.setAdapter(adapter);

            subTotalView.setText("$" + Double.toString(subTotal));
            disCountView.setText("$" + Double.toString(disCount));
            deliveryView.setText("$" + Double.toString(deliveryCharge));
            totalView.setText("$" + Double.toString(Math.min(0, subTotal + deliveryCharge + disCount)));

        } else {
            scrollView.setVisibility(View.GONE);
            noFoundItem.setVisibility(View.VISIBLE);
        }
    }

    public void BackEvent(View view) {
        finish();
    }
}