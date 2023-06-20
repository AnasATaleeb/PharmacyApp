package com.example.pharmacy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.R;

public class Conformation extends AppCompatActivity {
private Button btn;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_confirmation);
        getSupportActionBar().hide();

        // intilize the button
        btn = findViewById(R.id.btn_next);

        // set on click listener
        btn.setOnClickListener(v -> {
            //todo anas
            Intent intent = new Intent(Conformation.this, FinishOrder.class);
            startActivity(intent);
            finish();
        });
    }
}
