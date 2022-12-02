package com.example.e_commerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Adapter.FoodAdapter;
import com.example.e_commerce.Adapter.FoodCartAdapter;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public FoodCartAdapter adapter;
    private ScrollView scrollView;
    private TextView noFoundItem, buyAllBtn;
    public static TextView subTotalView, disCountView, deliveryView, totalView;
    private ArrayList<Food> foodList;
    private LocalCache localCache;

    public static double subTotal = 0;
    public static double disCount = 0;
    public static double deliveryCharge = 15;

    int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        addOnClickEvent();
    }

    private void addOnClickEvent() {
        buyAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date now = new Date();

                String day          = (String) DateFormat.format("dd", now);
                String monthString  = (String) DateFormat.format("MMM",  now);
                String year         = (String) DateFormat.format("yyyy", now);
                String hour         = (String) DateFormat.format("HH", now);
                String minute         = (String) DateFormat.format("mm", now);

                ArrayList<Food> foodList = localCache.loadFoodList();
                Gson gson = new Gson();
                ArrayList<String> stringList = new ArrayList<>();
                for (Food food : foodList)
                    stringList.add(gson.toJson(food));
                Map<String, String> order = new HashMap<>();
                order.put("user", localCache.loadUser().getEmail());
                order.put("food", gson.toJson(stringList));

                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Order");
                collectionReference.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    orderId = task.getResult().size() + 1;
                                    order.put("id", Integer.toString(orderId));
                                    order.put("subTotal", Double.toString(subTotal));
                                    order.put("disCount", Double.toString(disCount));
                                    order.put("deliveryCharge", Double.toString(deliveryCharge));
                                    order.put("date", monthString + " " + day + ", " + year);
                                    order.put("time", hour + ":" + minute);

                                    collectionReference.add(order)
                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    if (task.isSuccessful()) {
                                                        localCache.deleteFoodList();
                                                        Intent intent = new Intent(CartActivity.this, DetailOrderActivity.class);
                                                        intent.putExtra("OrderId", Integer.toString(orderId));
                                                        intent.putExtra("date", monthString + " " + day + ", " + year);
                                                        intent.putExtra("time", hour + ":" + minute);
                                                        intent.putExtra("subTotal", Double.toString(subTotal));
                                                        intent.putExtra("disCount", Double.toString(disCount));
                                                        intent.putExtra("deliveryCharge", Double.toString(deliveryCharge));
                                                        intent.putExtra("total", Double.toString(subTotal + deliveryCharge - disCount));
                                                        intent.putExtra("type", "cart");
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(CartActivity.this, "Error to buy", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(CartActivity.this, "Error to buy", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodCat);
        recyclerView.setLayoutManager(linearLayoutManager);
        scrollView = (ScrollView) findViewById(R.id.scrollView6);
        noFoundItem = (TextView) findViewById(R.id.noitemfound);
        buyAllBtn = (TextView) findViewById(R.id.buyAllBtn);
        localCache = new LocalCache(this, "Local cache");
        foodList = localCache.loadFoodList();

        subTotalView = (TextView) findViewById(R.id.subtotal);
        disCountView = (TextView) findViewById(R.id.discount);
        deliveryView = (TextView) findViewById(R.id.deliverycharge);
        totalView = (TextView) findViewById(R.id.total);

        if (foodList != null && foodList.size() > 0) {
            for (Food food : foodList) {
                subTotal += food.getCost() * food.getNumberInCart();
            }
            adapter = new FoodCartAdapter(CartActivity.this, foodList);
            recyclerView.setAdapter(adapter);

            subTotalView.setText("$" + Double.toString(subTotal));
            disCountView.setText("$" + Double.toString(disCount));
            deliveryView.setText("$" + Double.toString(deliveryCharge));
            totalView.setText("$" + Double.toString(Math.max(0, subTotal + deliveryCharge - disCount)));

        } else {
            scrollView.setVisibility(View.GONE);
            buyAllBtn.setVisibility(View.GONE);
            noFoundItem.setVisibility(View.VISIBLE);
        }
    }

    public void BackEvent(View view) {
        startActivity(new Intent(CartActivity.this, HomeActivity.class));
    }
}