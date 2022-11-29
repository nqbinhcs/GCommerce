package com.example.e_commerce.Model;

import android.os.Parcel;

import java.io.Serializable;

public class Food implements Serializable {

    private String title, seller, category, description, imgUrl;
    private double cost;
    private int numberInCart;

    public Food(String imgUrl, String title, String seller, String category, String description, double cost, int numberInCart) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.seller = seller;
        this.category = category;
        this.description = description;
        this.cost = cost;
        this.numberInCart = numberInCart;
    }

    public String getImageUrl() {
        return this.imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
