package com.example.e_commerce.Model;


public class Equipment {
    public int id;
    public String name;
    public String localizedName;
    public String image;
    public Temperature temperature;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getImage() {
        return image;
    }

    public Temperature getTemperature() {
        return temperature;
    }
}

