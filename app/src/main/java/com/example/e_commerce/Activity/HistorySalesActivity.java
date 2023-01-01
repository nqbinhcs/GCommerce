package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.e_commerce.Adapter.FoodAdapter;
import com.example.e_commerce.Adapter.FullPackageAdapter;
import com.example.e_commerce.Adapter.HistorySaleAdapter;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HistorySalesActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public HistorySaleAdapter adapter;
    private ImageView backButton;
    private TextView totalIncomeTextView;
    double totalIncome = 0;

    User user;
    public ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_sales);
        getUser();
        addEventBackButton();
        recyclerViewFoodCat();
    }

    private void getUser() {
        LocalCache localCache = new LocalCache(HistorySalesActivity.this, "Local cache");
        user = localCache.loadUser();
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


    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewHistorySales);
        recyclerView.setLayoutManager(linearLayoutManager);

        orderList = new ArrayList<Order>();


        // -------------------------Query from database-----------------------
        FirebaseFirestore.getInstance()
                .collection("Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String foodString = document.getString("food");
                                String[] foodList = foodString.split(",");
                                for (String field : foodList) {
                                    String[] parseField = field.split(":");
                                    //Log.d("PARSE", parseField[0]);
                                    if (parseField[0].equals("\\\"email\\\""))
                                    {
                                        String sellerName = "\\\"" + user.getEmail() + "\\\"";
                                        if (parseField[1].equals(sellerName)) {

                                            orderList.add(new Order(document.getString("id"),
                                                    document.getString("date"),
                                                    document.getString("time"),
                                                    Double.valueOf(document.getString("subTotal")),
                                                    Double.valueOf(document.getString("deliveryCharge")),
                                                    Double.valueOf(document.getString("disCount"))));
                                            totalIncome += Double.valueOf(document.getString("subTotal")) + Double.valueOf(document.getString("deliveryCharge"));

                                        }

                                    }
                                }
                            }

                            if (orderList.size() > 0) {
                                totalIncomeTextView = (TextView) findViewById(R.id.totalSalesNumber);
                                totalIncomeTextView.setText(Double.toString(totalIncome) + " $");
                                adapter = new HistorySaleAdapter(HistorySalesActivity.this, orderList);
                                recyclerView.setAdapter(adapter);
                            } else {
                                TextView noMatchView = (TextView) findViewById(R.id.noMatch);
                                noMatchView.setVisibility(View.VISIBLE);
                                ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView6);
                                scrollView.setVisibility(View.GONE);
                            }
                        } else {
                            Log.w("TAG2", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}