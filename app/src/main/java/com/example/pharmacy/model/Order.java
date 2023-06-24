package com.example.pharmacy.model;

import android.widget.ImageView;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private int patientId;
    private ArrayList<Item> items;
    private double totalPrice;

    private String name;

    private String location;

    private String status;

    public Order() {

    }

    public Order(int orderId,String name, int patientId, ArrayList<Item> items, double totalPrice) {
        this.orderId = orderId;
        this.name = name;
        this.patientId = patientId;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public Order(int orderId, int patientId, ArrayList<Item> items, double totalPrice, String name, String location, String status) {
        this.orderId = orderId;
        this.patientId = patientId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}