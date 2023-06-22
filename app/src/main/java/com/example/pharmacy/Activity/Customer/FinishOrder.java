package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pharmacy.R;

public class FinishOrder extends AppCompatActivity {
    AppCompatButton bac;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order);
        getSupportActionBar().hide();
        bac = findViewById(R.id.back_to_main);

        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishOrder.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}