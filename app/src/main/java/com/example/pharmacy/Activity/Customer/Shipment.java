package com.example.pharmacy.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;

public class Shipment extends AppCompatActivity {
    private EditText address,spinner;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_shipment);
        getSupportActionBar().hide();

        // 1. Initialize the UI components
        init();

        //2. Set the onClickListener for the button
        setOnClick();

        // todo set the spinner adapter
    }

    private void init() {
        spinner = findViewById(R.id.spinner_city);
        address = findViewById(R.id.edit_postal);
        btn = findViewById(R.id.btn_next);
    }

    private void setOnClick() {
        btn.setOnClickListener(v -> {
            if (spinner.getText().toString().isEmpty()){
                spinner.setError("الرجاء ادخال عنوان التوصيل");
            }else if(address.getText().toString().isEmpty()) {
                address.setError("الرجاء ادخال الرمز البريدي");
            }else{
                Intent intent = new Intent(Shipment.this, Payment.class);

                String itemJson1 = getIntent().getStringExtra("items_order");

                Type itemType = new TypeToken<ArrayList<Item>>() {}.getType();
                ArrayList<Item> itemList = new Gson().fromJson(itemJson1, itemType);

                double fprice =0.0;
                for (int i = 0; i <itemList.size() ; i++) {
                    fprice += Double.parseDouble(itemList.get(i).getPrice()) * itemList.get(i).getNumberOfItem();
                }

                Order order = new Order(itemList, fprice,"طلب", spinner.getText().toString(),Integer.parseInt(address.getText().toString()),"تم الطلب");
                String orderJson = new Gson().toJson(order);
                intent.putExtra("order", orderJson);
                startActivity(intent);
            }

        });
    }
}
