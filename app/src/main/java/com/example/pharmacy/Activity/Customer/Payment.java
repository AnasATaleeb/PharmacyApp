package com.example.pharmacy.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
            Intent intent = new Intent(Payment.this, Conformation.class);
            String orderJson1 = getIntent().getStringExtra("order");

            Order order =new Gson().fromJson(orderJson1, Order.class);
            String orderJson = new Gson().toJson(order);
            intent.putExtra("order", orderJson);
            startActivity(intent);
        });
    }


}
