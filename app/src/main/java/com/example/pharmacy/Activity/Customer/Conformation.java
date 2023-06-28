package com.example.pharmacy.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pharmacy.Adaptor.ConformationItemAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityPayConfirmationBinding;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Conformation extends AppCompatActivity {
    private Button btn;
    private ArrayList<Item> items;
    private ActivityPayConfirmationBinding binding;
    private TextView textView44, textViewShip;

    private FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int size = 0;

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

        Order order = new Gson().fromJson(orderJson, Order.class);

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
            String orderJson = getIntent().getStringExtra("order");

            Order order = new Gson().fromJson(orderJson, Order.class);

            //TODO: add order in FireStore
            mAuth = FirebaseAuth.getInstance();

            order.setKey(System.currentTimeMillis() + "");

            Map<String, String> dataToSave = new HashMap<>();
            dataToSave.put("name", order.getName());
            dataToSave.put("location", order.getLocation());
            dataToSave.put("status", order.getStatus());
            dataToSave.put("price", order.getTotalPrice() + "");
            dataToSave.put("postal", order.getPostalCode() + "");
            dataToSave.put("key",order.getKey());


            db.collection("Orders").document(mAuth.getUid())
                    .set(dataToSave,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast toast = Toast.makeText(Conformation.this, "تم ارسال طلبك 🥳", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("خطأ :", e.toString());
                            Toast toast = Toast.makeText(Conformation.this, "خطأ في ارسال الطلب 😥", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

            ArrayList<Item> items1 = order.getItems();

            for (Item item : items1) {
                Map<String, String> data = new HashMap<>();
                data.put("name", item.getTitle());
                data.put("category", item.getCategory());
                data.put("description", item.getDiscreption());
                data.put("price", item.getPrice());
                data.put("size", item.getQuantity() + "");
                data.put("image", item.getPic());
                data.put("numberOfItem", item.getNumberOfItem() + "");
                db.collection("Orders").document(mAuth.getUid()).collection(order.getKey())
                        .add(data).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("خطأ :", e.toString());
                                Toast toast = Toast.makeText(Conformation.this, "خطأ في اضافة المنتج في الطلب 😥", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
            }

            startActivity(intent);
            finish();
        });
    }
}
