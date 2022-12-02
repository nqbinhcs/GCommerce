package com.example.e_commerce.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, sellerTxt, shopLocationTxT, categoryTxT, descriptionTxt, numberOrderTxt, descriptionBtn;
    private ImageView plusBtn, minusBtn, picFood, backButton, directionBtn;
    private Food object;
    int numberOrder = 1;
    LocalCache localCache;
    private boolean visible = false;

    public ArrayList<User> userShops;
//    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

//        managementCart = new ManagementCart(this);
        addEventBackButton();
        initView();
        setUserShopDatabase();
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

    public void setUserShopDatabase(){
        userShops = new ArrayList<User>();
        // -------------------------Query from database----------------------- ==> error :<

        userShops.add(new User("hvson@gmail.com", "Son Ho", 10.7379092, 106.677294, "39 D. Cao Lo Street, Ward 4, District 8, Ho Chi Minh City, Vietnam", "SH Store"));
        userShops.add(new User("vghuy@gmail.com", "Huy Vuong", 10.7715101, 106.6677586, "704 Su Van Hanh, Ward 12, District 10, Ho Chi Minh City, Vietnam", "HV Store"));
        userShops.add(new User("nqbinh@gmail.com", "Binh Nguyen", 10.7598163, 106.6592192, "497 Hoa Hao, Ward 7, District 10, Ho Chi Minh City", "BN Store"));

    }

    public User getUserShop(String userName){

        // -------------------------Query from database----------------------- ==> error :<

        User res = userShops.get(0);
        for(int i = 1; i < userShops.size(); i++){
            if(userShops.get(i).getName().equals(userName)){
                res = userShops.get(i);
            }
        }

        return res;
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


        shopLocationTxT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddressStore();
            }
        });


        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Food> foodList = localCache.loadFoodList();
                if (foodList == null)
                    foodList = new ArrayList<>();
                boolean added = false;
                for (Food food : foodList) {
                    if (food.equalTo(object)) {
                        food.setNumberInCart(food.getNumberInCart() + object.getNumberInCart());
                        added = true;
                        break;
                    }
                }
                if (!added)
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
                    descriptionBtn.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_baseline_expand_less_24), null, null, null);
                    //descriptionBtn.setCompoundDrawablePadding(10);

                } else {
                    descriptionTxt.setVisibility(View.GONE);
                    descriptionBtn.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_baseline_expand_more_24), null, null, null);
                    //descriptionBtn.setCompoundDrawablePadding(10);
                }
            }
        });

        directionBtn.setOnClickListener(new View.OnClickListener() {

            // Hard code current location
            LatLng currentLocation = new LatLng(10.888249399024446,106.78917099714462);

            LatLng shopLocation = getUserShop(object.getSeller()).getCoordinateAddress();

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + Double.valueOf(currentLocation.latitude) + "," + Double.valueOf(currentLocation.longitude) + "&daddr="+ String.valueOf(shopLocation.latitude) +","+ String.valueOf(shopLocation.longitude)));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


    }

    private void showAddressStore() {
        Intent intent = new Intent(ShowDetailActivity.this, MapsActivity.class);

//      --------------------------Query user from database email of product-----------------------------
        User user = getUserShop(object.getSeller());
        intent.putExtra("user_store", (Serializable) user);
        startActivity(intent);
    }

    private void initView() {
        localCache = new LocalCache(this, "Local cache");
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        sellerTxt = findViewById(R.id.sellerTxt);
        shopLocationTxT = findViewById(R.id.shopLocationTxt);
        categoryTxT = findViewById(R.id.categoryTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        descriptionBtn = findViewById(R.id.descriptionBtn);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        picFood=findViewById(R.id.picfood);
        directionBtn = findViewById(R.id.directionImg);
    }
}
