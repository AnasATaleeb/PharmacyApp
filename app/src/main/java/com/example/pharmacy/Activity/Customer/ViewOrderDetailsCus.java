package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.pharmacy.Adaptor.ConformationItemAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityViewOrderDetailsBinding;
import com.example.pharmacy.databinding.ActivityViewOrderDetailsCusBinding;
import com.example.pharmacy.model.Order;
import com.google.gson.Gson;

public class ViewOrderDetailsCus extends AppCompatActivity {

    ActivityViewOrderDetailsCusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewOrderDetailsCusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        String s = getIntent().getStringExtra("order");
        Log.v("S = ", s);
        Gson gson = new Gson();
        Order o = gson.fromJson(s, Order.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsOder.setLayoutManager(linearLayoutManager);

        ConformationItemAdapter adapter = new ConformationItemAdapter(this, o.getItems());
        binding.itemsOder.setAdapter(adapter);


    }
}