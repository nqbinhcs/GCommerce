package com.example.e_commerce.Model;

import android.os.Parcel;

import java.io.Serializable;

public class Food implements Serializable {

    private double cost;
    private String category;
    private String description;
    private String imgUrl;
    private int numberInCart;
    private String seller;
    private String title;
    private String email;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return this.email;
    }

    public Food(String email, String imgUrl, String title, String seller, String category, String description, double cost, int numberInCart) {
        this.email = email;
        this.imgUrl = imgUrl;
        this.title = title;
        this.seller = seller;
        this.category = category;
        this.description = description;
        this.cost = cost;
        this.numberInCart = numberInCart;
        this.numberInCart = 1;
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

    @Override
    public String toString() {
        return "Food{" +
                "title='" + title + '\'' +
                ", seller='" + seller + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", cost=" + cost +
                ", numberInCart=" + numberInCart +
                '}';
    }

    public boolean equalTo(Food other) {
        return  this.imgUrl.equals(other.imgUrl) &&
                this.seller.equals(other.seller);

    }
}
