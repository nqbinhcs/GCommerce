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
import com.example.e_commerce.Adapter.HistoryOrderAdapter;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class HistoryOrderActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public HistoryOrderAdapter adapter;
    private ImageView backButton;

    User user;
    public ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        getUser();
        addEventBackButton();
        recyclerViewFoodCat();
    }

    private void getUser() {
        LocalCache localCache = new LocalCache(HistoryOrderActivity.this, "Local cache");
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
        recyclerView = findViewById(R.id.recyclerViewHistoryOrder);
        recyclerView.setLayoutManager(linearLayoutManager);

        orderList = new ArrayList<Order>();
        // -------------------------Query from database-----------------------
        FirebaseFirestore.getInstance()
                .collection("Order")
                .whereEqualTo("user", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                orderList.add(new Order(document.getString("id"),
                                        document.getString("date"),
                                        document.getString("time"),
                                        Double.valueOf(document.getString("subTotal")),
                                        Double.valueOf(document.getString("deliveryCharge")),
                                        Double.valueOf(document.getString("disCount"))));
                            }
                            if (orderList.size() > 0) {
                                adapter = new HistoryOrderAdapter(HistoryOrderActivity.this, orderList);
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