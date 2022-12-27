package com.example.e_commerce.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Step implements Parcelable {

    public Step() {
    }

    public Step(int number, String step, ArrayList<Ingredient> ingredients, Length length) {
        this.number = number;
        this.step = step;
        this.ingredients = ingredients;
        this.length = length;
    }

    private int number;
    private String step;
    private ArrayList<Ingredient> ingredients;
    private Length length;

    protected Step(Parcel in) {
        number = in.readInt();
        step = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(step);
    }
}
