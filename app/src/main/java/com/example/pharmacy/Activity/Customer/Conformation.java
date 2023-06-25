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

import java.util.ArrayList;

public class Conformation extends AppCompatActivity {
    private Button btn;
    private ArrayList<Item> items;
    private ActivityPayConfirmationBinding binding;
    private TextView textView44;

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
        items.add(new Item("بانادول", "لعلاج البرد والرشخ والزكام - 20 قرص", R.drawable.panadolextra, 24.5, 7));
        items.add(new Item("بانادول", "لعلاج البرد والرشخ والزكام - 20 قرص", R.drawable.panadolextra, 23.38, 1));
        items.add(new Item("بانادول", "لعلاج البرد والرشخ والزكام - 20 قرص", R.drawable.panadolextra, 65.2, 132));
        items.add(new Item("بانادول", "لعلاج البرد والرشخ والزكام - 20 قرص", R.drawable.panadolextra, 55.1, 13));
        items.add(new Item("بانادول", "لعلاج البرد والرشخ والزكام - 20 قرص", R.drawable.panadolextra, 89.5, 10));

        Order o = (new Order(1, "طلب رقم #1", 1, items, 67.5));

        textView44.setText("إجمالي الطلب: " + o.getTotalPrice() + " شيكل");
        ConformationItemAdapter adapter = new ConformationItemAdapter(this, o.getItems());
        binding.itemsOder.setAdapter(adapter);
    }

    // intilize the button
    private void initialize() {
        btn = findViewById(R.id.btn_next);
        textView44 = findViewById(R.id.textView44);
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
