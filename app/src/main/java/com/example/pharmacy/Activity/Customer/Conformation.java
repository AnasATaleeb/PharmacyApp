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

import com.example.pharmacy.Activity.ViewItem;
import com.example.pharmacy.Adaptor.ConformationItemAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityPayConfirmationBinding;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Conformation extends AppCompatActivity {
    private Button btn;
    private ArrayList<Item> items;
    private ActivityPayConfirmationBinding binding;
    private TextView textView44,textViewShip;

    private FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


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
            String orderJson = getIntent().getStringExtra("order");

            Order order =new Gson().fromJson(orderJson, Order.class);

            //TODO: add order in FireStore
            mAuth = FirebaseAuth.getInstance();

            Map<String, String> dataToSave = new HashMap<>();
            dataToSave.put("name",order.getName());
            dataToSave.put("location", order.getLocation());
            dataToSave.put("satus",order.getStatus());
            dataToSave.put("price",order.getTotalPrice()+"");
            db.collection("Orders").document(mAuth.getUid())
                    .set(dataToSave, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener() {
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

            for (Item item: items1) {
                Map<String, String> dataToSave1 = new HashMap<>();
                dataToSave1.put("name",item.getTitle());
                dataToSave1.put("category", item.getCategory());
                dataToSave1.put("description",item.getDiscreption());
                dataToSave1.put("price",item.getPrice());
                dataToSave1.put("size",item.getQuantity()+"");
                dataToSave1.put("image",item.getPic());
                dataToSave1.put("numberOfItem",item.getNumberOfItem()+"");
                db.collection("Orders").document(mAuth.getUid()).collection("OrderItems")
                        .add(dataToSave1).addOnSuccessListener(new OnSuccessListener() {
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
