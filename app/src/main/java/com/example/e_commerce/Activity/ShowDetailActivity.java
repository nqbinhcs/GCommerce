package com.example.e_commerce.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

import java.util.ArrayList;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, sellerTxt, categoryTxT, descriptionTxt, numberOrderTxt, descriptionBtn;
    private ImageView plusBtn, minusBtn, picFood, backButton;
    private Food object;
    int numberOrder = 1;
    LocalCache localCache;
//    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

//        managementCart = new ManagementCart(this);
        addEventBackButton();
        initView();
        getBundle();
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

    private void getBundle() {
        object = (Food) getIntent().getSerializableExtra("object");

        Glide.with(this)
                .load(object.getImageUrl())
                .into(picFood);

        titleTxt.setText(object.getTitle());
        feeTxt.setText("$" + object.getCost());

        sellerTxt.setText(object.getSeller());
        categoryTxT.setText(object.getCategory());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOrder = numberOrder + 1;
                object.setNumberInCart(numberOrder);
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                object.setNumberInCart(numberOrder);
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Food> foodList = localCache.loadFoodList();
                if (foodList == null)
                    foodList = new ArrayList<>();
                foodList.add(object);
                localCache.deleteFoodList();
                localCache.addFoodList(foodList);
                Toast.makeText(ShowDetailActivity.this, "Add " + Integer.toString(numberOrder) + " " + object.getTitle() + " successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        descriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descriptionTxt.getVisibility() == View.GONE) {
                    descriptionTxt.setVisibility(View.VISIBLE);
                    descriptionBtn.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.detail_description), null, null, null);
                } else {
                    descriptionTxt.setVisibility(View.GONE);
                    descriptionBtn.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_baseline_expand_more_24), null, null, null);
                    descriptionBtn.setCompoundDrawablePadding(0);
                }
            }
        });
    }

    private void initView() {
        localCache = new LocalCache(this, "Local cache");
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        sellerTxt = findViewById(R.id.sellerTxt);
        categoryTxT = findViewById(R.id.categoryTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        descriptionBtn = findViewById(R.id.descriptionBtn);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood=findViewById(R.id.picfood);
    }
}
