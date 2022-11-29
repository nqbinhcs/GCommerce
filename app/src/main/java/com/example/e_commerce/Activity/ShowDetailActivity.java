package com.example.e_commerce.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.R;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, sellerTxt, categoryTxT, descriptionTxt, numberOrderTxt;
    private ImageView plusBtn, minusBtn, picFood, backButton;
    private Food object;
    int numberOrder = 1;
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
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

//        addToCartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                object.setNumberInCart(numberOrder);
//                managementCart.insertFood(object);
//            }
//        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        sellerTxt = findViewById(R.id.sellerTxt);
        categoryTxT = findViewById(R.id.categoryTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood=findViewById(R.id.picfood);
    }
}