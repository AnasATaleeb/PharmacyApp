package com.example.pharmacy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.R;

public class Payment extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_payment);
        getSupportActionBar().hide();

        // intilize the button
        btn = findViewById(R.id.btn_next);

        // set on click listener
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(Payment.this, MainActivity.class);
            startActivity(intent);
        });
    }


}
