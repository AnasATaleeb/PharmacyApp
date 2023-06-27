package com.example.pharmacy.model;


import java.util.ArrayList;

public class Item {
    private String title;
    private String discreption;
    private String pic;
    private String price;

    private int quantity;

    private String category;
    private int  numberOfItem =1;

    public static final ArrayList<Item> categories = new ArrayList<>();

    public Item(){

    }
    public Item(String title, String discreption, String pic, String price) {
        this.title = title;
        this.discreption = discreption;
        this.pic = pic;
        this.price = price;
    }

    public Item(String title, String discreption, String pic, String price, int quantity) {
        this.title = title;
        this.discreption = discreption;
        this.pic = pic;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(String title, String discreption, String pic, String price, int quantity, String category) {
        this.title = title;
        this.discreption = discreption;
        this.pic = pic;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Item(String title, String discreption, String pic, String price, int quantity, String category, int numberOfItem) {
        this.title = title;
        this.discreption = discreption;
        this.pic = pic;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.numberOfItem = numberOfItem;
    }

    public int getNumberOfItem() {
        return numberOfItem;
    }

    public void setNumberOfItem(int numberOfItem) {
        this.numberOfItem = numberOfItem;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDiscreption() {
        return discreption;
    }

    public void setDiscreption(String discreption) {
        this.discreption = discreption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", discreption='" + discreption + '\'' +
                ", pic='" + pic + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
