package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.pharmacy.Adaptor.ConformationItemAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityViewOrderDetailsBinding;
import com.example.pharmacy.databinding.ActivityViewOrderDetailsCusBinding;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ViewOrderDetailsCus extends AppCompatActivity {

    ActivityViewOrderDetailsCusBinding binding;
    TextView textView48,final_price,textViewShip;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewOrderDetailsCusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // hide the action bar
        getSupportActionBar().hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 1. Initialize the UI components
        intializeUI();

        String s = getIntent().getStringExtra("order");
        Gson gson = new Gson();
        Order o = gson.fromJson(s, Order.class);

        textViewShip.setText(o.getLocation());
        final_price.setText(" إجمالي الطلب: " + o.getTotalPrice() + " شيكل");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsOder.setLayoutManager(linearLayoutManager);

        ConformationItemAdapter adapter = new ConformationItemAdapter(this, o.getItems());
        binding.itemsOder.setAdapter(adapter);

    }

    private void intializeUI() {
        textView48 = findViewById(R.id.textView48);
        final_price = findViewById(R.id.final_price);
        textViewShip = findViewById(R.id.textViewShip);
    }

    private void updateOrderStatus(String id) {
        Map<String, Object> order = new HashMap<>();
        order.put("status", "تم الشحن");

        db.collection("orders").document(id)
                .update(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "Order status updated successfully!");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating order status", e);
                    }
                });
    }
}