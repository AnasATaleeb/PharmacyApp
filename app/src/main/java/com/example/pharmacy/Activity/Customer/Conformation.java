package com.example.pharmacy.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pharmacy.Adaptor.ConformationItemAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityPayConfirmationBinding;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Conformation extends AppCompatActivity {
    private Button btn;
    private ArrayList<Item> items;
    private ActivityPayConfirmationBinding binding;
    private TextView textView44,textViewShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPayConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // hide action bar
        getSupportActionBar().hide();

        // intilize the button
        initialize();

        // set on click listener
        setOnClick();

        // set up the recycler view
        SetUp();
    }

    private void SetUp() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsOder.setLayoutManager(linearLayoutManager);

        items = new ArrayList<>();
        String orderJson = getIntent().getStringExtra("order");

        Order order =new Gson().fromJson(orderJson, Order.class);

        textView44.setText("إجمالي الطلب: " + order.getTotalPrice() + " شيكل");
        textViewShip.setText(order.getLocation());
        ConformationItemAdapter adapter = new ConformationItemAdapter(this, order.getItems());
        binding.itemsOder.setAdapter(adapter);
    }

    // intilize the button
    private void initialize() {
        btn = findViewById(R.id.btn_next);
        textView44 = findViewById(R.id.final_price);
        textViewShip = findViewById(R.id.textViewShip);
    }

    // set on click listener
    private void setOnClick() {
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(Conformation.this, FinishOrder.class);
            startActivity(intent);
            finish();
        });
    }
}
