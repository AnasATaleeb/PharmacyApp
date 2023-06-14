package com.example.pharmacy.model;


import java.util.ArrayList;

public class Category {
    private String title;
    private int pic;

    public static final ArrayList<Category> categories = new ArrayList<>();


    public Category(String title, int pic){
        this.pic=pic;
        this.title=title;
    }

    public static ArrayList<Category> getCategory() {
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
}
