package com.example.e_commerce.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Manager.LocalCache;
import com.example.e_commerce.Model.Food;
import com.example.e_commerce.Model.User;
import com.example.e_commerce.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    FusedLocationProviderClient fusedLocationProviderClient;

    User userShop = null;
    boolean locationPermissionGranted;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    LatLng currentLocation, shopLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        object = (Food) getIntent().getSerializableExtra("object");

        // get user shop from database
        FirebaseFirestore.getInstance()
                .collection("User")
                .whereEqualTo("email", object.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("HelloShowdetail", document.getString("longitude"));
                                userShop = new User(document.getString("email"),
                                        document.getString("name"),
                                        Double.parseDouble(document.getString("latitude")),
                                        Double.parseDouble(document.getString("longitude")),
                                        document.getString("address"),
                                        document.getString("shop_name"));
                            }
                        }
                    }
                });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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

    private User getUserShop() {
        if (userShop != null)
            return userShop;
        return new User();
    }

//    private void getLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        locationPermissionGranted = false;
//        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationPermissionGranted = true;
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

    public void goToDirect() {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + Double.valueOf(currentLocation.latitude) + "," + Double.valueOf(currentLocation.longitude) + "&daddr=" + String.valueOf(shopLocation.latitude) + "," + String.valueOf(shopLocation.longitude)));
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void getBundle() {

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


            @Override
            public void onClick(View view) {
                currentLocation = new LatLng(10.888249399024446, 106.78917099714462);
                shopLocation = getUserShop().getCoordinateAddress();

                if (ActivityCompat.checkSelfPermission(ShowDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(ShowDetailActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if (location != null)
                                            currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                                        goToDirect();
                                    }
                                });
                } else {
                    goToDirect();
                }
            }
        });


    }

    private void showAddressStore() {
        Intent intent = new Intent(ShowDetailActivity.this, MapsActivity.class);

//      --------------------------Query user from database email of product-----------------------------
        User user = getUserShop();
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
