package com.example.pharmacy.model;

import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;

public class Patient {
    private String name;
    private String phoneNumber;
    private String email;
    private String location;
    private String image;

    private ArrayList<Item> favorateList;

    private ArrayList<Item> cartList;

    private ArrayList<Order> orderList;

    public Patient(String name, String phoneNumber, String email, String location, String image) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.image = image;
    }

    public Patient(String name, String phoneNumber, String email, String location) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
    }

    public Patient(String name, String phoneNumber, String email, String location, String image, ArrayList<Item> favorateList, ArrayList<Item> cartList, ArrayList<Order> orderList) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.image = image;
        this.favorateList = favorateList;
        this.cartList = cartList;
        this.orderList = orderList;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
