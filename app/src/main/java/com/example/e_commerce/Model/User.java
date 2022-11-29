package com.example.e_commerce.Model;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.maps.model.LatLng;

public class User {
    private String email;
    private String name;
    private LatLng address;
    private String shop_name;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LatLng getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(LatLng address) {
        this.address = address;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public User() {

    }

    public User(String email, String name, double latitude, double longitude, String shop_name) {
        this.email = email;
        this.name = name;
        this.address = new LatLng(latitude, longitude);
        this.shop_name = shop_name;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", shop_name='" + shop_name + '\'' +
                '}';
    }
}
