package com.example.e_commerce.Model;

public class Order {
    private String id;
    private String date, time;
    private double subtotal, deliveryCharge, discount;

    Order() {

    }

    public Order(String id, String date, String time, double subtotal, double deliveryCharge, double discount) {
        this.id = id;
        this.date = date;
        this.subtotal = subtotal;
        this.deliveryCharge = deliveryCharge;
        this.discount = discount;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return Math.max(0, subtotal + deliveryCharge - discount);
    }
}
