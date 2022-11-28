package com.example.e_commerce.Model;

import android.os.Parcel;

import java.io.Serializable;

public class Food implements Serializable {

    private String id;
    private String title, pic, seller, category, description;
    private double fee;
    private double averageRating;
    private int numberInCart;

    public Food(){ }

    public Food(String id, String name, String pic, String seller, String category, String description, Double fee, Double averageRating, int numberInCart) {
        this.id = id;
        this.title = name;
        this.pic = pic;
        this.seller = seller;
        this.category = category;
        this.description = description;
        this.fee = fee;
        this.averageRating = averageRating;
        this.numberInCart = numberInCart;
    }


    protected Food(Parcel in) {
        id = in.readString();
        title = in.readString();
        pic = in.readString();
        description = in.readString();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
