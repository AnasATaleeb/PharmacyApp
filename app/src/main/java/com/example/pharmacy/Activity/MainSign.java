package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pharmacy.R;

public class MainSign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_sign);
    }
}