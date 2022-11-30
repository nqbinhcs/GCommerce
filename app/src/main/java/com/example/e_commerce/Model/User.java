package com.example.e_commerce.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;


public class User implements Serializable, Parcelable {
    private String email;
    private String name;
    double lat, lng;
    private String address; //227 Nguyen Van Cu
    private String shop_name;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getCoordinateAddress() {
        return new LatLng(lat,lng);
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }




    public User() {

    }

    public User(String email, String name, double latitude, double longitude, String address, String shop_name) {
        this.email = email;
        this.name = name;
        this.lat = latitude;
        this.lng = longitude;
        this.address = address;
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

    @Override
    public int describeContents() {
        return 0;
    }

    protected User(Parcel in) {
        email = in.readString();
        name = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        address = in.readString();
        shop_name = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(address);
        dest.writeString(shop_name);
    }
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
