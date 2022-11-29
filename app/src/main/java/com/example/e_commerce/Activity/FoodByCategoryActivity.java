package com.example.e_commerce.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Adapter.FoodAdapter;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    }


    private void recyclerViewFoodCat() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewFoodCat);
        recyclerView.setLayoutManager(linearLayoutManager);

        foodList = new ArrayList<Food>();
        // -------------------------Query from database-----------------------
//        foodList.add(new Food("https://firebasestorage.googleapis.com/v0/b/online-market-d8999.appspot.com/o/images%2F2022_11_28_23_35_01?alt=media&token=593fb45e-37e2-46f4-a842-0634d2344bec", "Bell Peper Red", "Binh", "Vegetable", "Like the tomato, bell peppers are botanical fruits but culinary vegetables. Pieces of bell pepper are commonly used in garden salads and as toppings on pizza", 34.0, 1));
        FirebaseFirestore.getInstance()
                .collection("Product")
                .whereEqualTo("category", catTitle)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG2", document.getId() + " => " + document.getData());

                                foodList.add(new Food(document.getString("imgUrl"),
                                    document.getString("name"),
                                    document.getString("seller"),
                                    document.getString("category"),
                                    document.getString("description"),
                                    Double.valueOf(document.getString("cost")), 0));
                            }

                            adapter = new FoodAdapter(foodList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.w("TAG2", "Error getting documents.", task.getException());
                        }
                    }
                });

    }


}