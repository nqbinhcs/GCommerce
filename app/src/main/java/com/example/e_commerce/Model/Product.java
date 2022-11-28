package com.example.e_commerce.Model;

public class Product {
    private String img_url;
    private String name;
    private int cost;
    private int amount;
    private String shop;

    public Product(String img_url, String name, int cost, int amount, String shop) {
        this.img_url = img_url;
        this.name = name;
        this.cost = cost;
        this.amount = amount;
        this.shop = shop;
    }

    public String getShop() {
        return "Shop: " + shop;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return Integer.toString(cost) + " VNĐ";
    }

    public String getAmount() {
        return "Number: " + Integer.toString(amount);
    }
}
