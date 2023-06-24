package com.example.pharmacy.Activity.DoctorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pharmacy.R;

import java.util.Objects;

public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        // Hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}