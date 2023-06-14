package com.example.pharmacy.model;


import java.util.ArrayList;

public class Item {
    private String title;
    private String discreption;
    private int pic;

    private double price;

    public static final ArrayList<Item> categories = new ArrayList<>();


    public Item(String title, String discreption, int pic, double price) {
        this.title = title;
        this.discreption = discreption;
        this.pic = pic;
        this.price = price;
    }

    public static ArrayList<Item> getCategory() {
        return categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getDiscreption() {
        return discreption;
    }

    public void setDiscreption(String discreption) {
        this.discreption = discreption;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
