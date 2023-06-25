package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pharmacy.R;

import java.util.Objects;

public class searchItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        // Hide the action bar
        getSupportActionBar().hide();
    }
}