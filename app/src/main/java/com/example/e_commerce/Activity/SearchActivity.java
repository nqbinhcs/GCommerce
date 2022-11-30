package com.example.e_commerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity  {

    public RecyclerView recyclerView;
    public FoodAdapter adapter;
    private ImageView backButton;
    private String searchText;

    public ArrayList<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
//                finish();
                startActivity(new Intent(SearchActivity.this, HomeActivity.class));
            }
        });
    }

    private void getBundle() {
        searchText = (String) getIntent().getStringExtra("Search text");
        TextView searchName = (TextView) findViewById(R.id.catNameTxT);
        searchName.setText("Search: " + searchText);
        searchText = searchText.toLowerCase();
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
        FirebaseFirestore.getInstance()
                .collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("name") != null && document.getString("name").toLowerCase().contains(searchText)) {
                                    foodList.add(new Food(document.getString("imgUrl"),
                                            document.getString("name"),
                                            document.getString("seller"),
                                            document.getString("category"),
                                            document.getString("description"),
                                            Double.valueOf(document.getString("cost")), 0));
                                }
                            }
                            if (foodList.size() > 0) {
                                adapter = new FoodAdapter(foodList);
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