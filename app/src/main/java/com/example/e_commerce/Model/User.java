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

    public String getShop_name() {
        return shop_name;
    }

    public User(String email, String name, LatLng address, String shop_name) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.shop_name = shop_name;
    }

    public User(String email) {
        this.email = email;
        FirebaseFirestore.getInstance()
                .collection("User")
                .whereEqualTo("email", this.email)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name = document.getString("name");
                                GeoPoint geoPoint = document.getGeoPoint("address");
                                address = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                                shop_name = document.getString("shop_name");
                            }
                        } else {
                            Log.d("Firebase query", "Error getting documents: ", task.getException());
                        }
                    }
                });
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
