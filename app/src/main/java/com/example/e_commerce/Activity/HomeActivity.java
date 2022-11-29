package com.example.e_commerce.Activity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.Adapter.CategoryAdapter;
import com.example.e_commerce.Adapter.PopularAdapter;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Category;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.Model.User;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private LocalCache localCache;
    private User user;

    private RecyclerView.Adapter categoryAdapter, popularAdapter;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private EditText searchView;

    private FloatingActionButton cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        loadUserName();
        recyclerViewCategory();
        recyclerViewPopular();
        addSearchEvent();
        addCameraEvent();
        bottomNavigation();
    }

    private void initView() {
        cameraButton = findViewById(R.id.cameraFloatBtn);
        searchView = findViewById(R.id.searchEditText);
        localCache = new LocalCache(HomeActivity.this, "Local cache");
    }

    private void loadUserName() {
        user = localCache.loadUser();
        TextView userNameView = (TextView) findViewById(R.id.usernameTxT);
        userNameView.setText(user.getName());
    }

    private void addCameraEvent() {
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CameraActivity.class));
            }
        });
    }

    private void addSearchEvent() {
        searchView.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchView.getRight() - searchView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - searchView.getPaddingRight())) {
                        if (searchView.getText().toString().length() > 0) {
                            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                            intent.putExtra("Search text", searchView.getText().toString());
                            startActivity(intent);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cameraFloatBtn);
//        LinearLayout homeButtonn = findViewById(R.id.homeBtn);
        LinearLayout profileButtonn = findViewById(R.id.profileBtn);
        LinearLayout categoryButton = findViewById(R.id.categoryBtn);
        LinearLayout cartButtonn = findViewById(R.id.cartBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CameraActivity.class));
            }
        });

        profileButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
            }
        });

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddProductActivity.class));
            }
        });

        cartButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });
    }

    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList=findViewById(R.id.categoryRecyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<Category> category = new ArrayList<>();

        // -------------------------Query from database-----------------------
        category.add(new Category("001","Fruit", "cat_11"));
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
        FirebaseFirestore.getInstance().collection("Product").limit(10).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                foodList.add(new Food(document.getString("imgUrl"),
                                        document.getString("name"),
                                        document.getString("seller"),
                                        document.getString("category"),
                                        document.getString("description"),
                                        Double.valueOf(document.getString("cost")), 0));
                            }
                            popularAdapter = new PopularAdapter(foodList);
                            recyclerViewPopularList.setAdapter(popularAdapter);
                        } else {
                            Log.w("HomeActivity", "Error loading popular data", task.getException());
                        }
                    }
                });


    }
}