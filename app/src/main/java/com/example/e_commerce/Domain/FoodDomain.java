package com.example.e_commerce.Domain;

import android.os.Parcel;

import java.io.Serializable;

public class FoodDomain implements Serializable {

    private String id;
    private String title, pic, description;
    private double fee;
    private String averageRating;

    public FoodDomain(){ }

    public FoodDomain(String id, String name, String pic, String description, Double fee, String averageRating) {
        this.id = id;
        this.title = name;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.averageRating = averageRating;
    }


    protected FoodDomain(Parcel in) {
        id = in.readString();
        title = in.readString();
        pic = in.readString();
        description = in.readString();
        averageRating = in.readString();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
