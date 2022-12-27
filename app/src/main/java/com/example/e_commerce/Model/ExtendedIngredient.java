package com.example.e_commerce.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ExtendedIngredient implements Parcelable {
    private int id;
    private String image, name, unit;
    private double amount;
    private Metric metric;

    public ExtendedIngredient() {
    }

    public ExtendedIngredient(int id, String image, String name, String unit, double amount, Metric metric) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.unit = unit;
        this.amount = amount;
        this.metric = metric;
    }

    protected ExtendedIngredient(Parcel in) {
        id = in.readInt();
        image = in.readString();
        name = in.readString();
        unit = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<ExtendedIngredient> CREATOR = new Creator<ExtendedIngredient>() {
        @Override
        public ExtendedIngredient createFromParcel(Parcel in) {
            return new ExtendedIngredient(in);
        }

        @Override
        public ExtendedIngredient[] newArray(int size) {
            return new ExtendedIngredient[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(unit);
        dest.writeDouble(amount);
    }
}
