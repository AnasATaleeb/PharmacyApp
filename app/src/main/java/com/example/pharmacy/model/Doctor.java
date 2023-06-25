package com.example.pharmacy.model;

import android.widget.ImageView;

import java.net.URL;
import java.util.ArrayList;

public class Doctor {
    private String name;
    private String phoneNumber;
    private String email;
    private String location;
    private String image;

    public Doctor(String name, String phoneNumber, String email, String location) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
    }

    public Doctor(String name, String phoneNumber, String email, String location, String image) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
        this.image = image;
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
}
