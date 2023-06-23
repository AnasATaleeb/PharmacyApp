package com.example.pharmacy.model;

import android.media.Image;

import java.util.ArrayList;

public class Patient {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String location;
    private Image image;

    private ArrayList<Item> favorateList;

    private ArrayList<Item> cartList;

    private ArrayList<Order> orderList;

    public Patient(int id, String name, String phoneNumber, String email, String location, Image image) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.image = image;
    }

    public Patient(int id, String name, String phoneNumber, String email, String location, Image image, ArrayList<Item> favorateList, ArrayList<Item> cartList, ArrayList<Order> orderList) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.image = image;
        this.favorateList = favorateList;
        this.cartList = cartList;
        this.orderList = orderList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ArrayList<Item> getFavorateList() {
        return favorateList;
    }

    public void setFavorateList(ArrayList<Item> favorateList) {
        this.favorateList = favorateList;
    }

    public ArrayList<Item> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<Item> cartList) {
        this.cartList = cartList;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }
}
