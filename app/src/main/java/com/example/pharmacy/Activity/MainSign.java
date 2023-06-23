package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pharmacy.R;


public class MainSign extends AppCompatActivity {

    private Button login;
    private Button signup;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_sign);

        login = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSign.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSign.this, Register.class);
                startActivity(intent);
            }
        });

    }
}