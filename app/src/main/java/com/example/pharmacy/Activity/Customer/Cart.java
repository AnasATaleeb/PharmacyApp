package com.example.pharmacy.Activity.Customer;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.ViewItem;
import com.example.pharmacy.Adaptor.ConformationItemAdapter;
import com.example.pharmacy.Adaptor.ItemsAdapter;
import com.example.pharmacy.Adaptor.LoveAdapter;
import com.example.pharmacy.Decorator.GridSpacingItemDecoration;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityCartBinding;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Cart extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    Intent intent;

    ActivityCartBinding binding;
    private FirebaseAuth mAuth;
    ArrayList<Item> items;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView final_price;
    Button comfBtn;
    double fprice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        bottomNavigationSetUp();

        comfBtn = findViewById(R.id.comfBtn);
        final_price = findViewById(R.id.final_price);

        comfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: put order in puString json
                intent = new Intent(Cart.this,Shipment.class);
                String itemJson = new Gson().toJson(items);
                intent.putExtra("items_order", itemJson);
                startActivity(intent);
            }
        });

       items = new ArrayList<>();

        db.collection("Users").document(mAuth.getUid()).collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.getString("name");
                                String description = document.getString("description");
                                String pic = document.getString("image");
                                String price = document.getString("price");
                                int quantity = Integer.parseInt(document.getString("size"));
                                String category = document.getString("category");
                                int numberOfItem = Integer.parseInt(document.getString("numberOfItem"));
                                Item item = new Item(title, description, pic, price, quantity, category,numberOfItem);


                                fprice += Double.parseDouble(item.getPrice());
                                final_price.setText( "اجمالي الطلب : "+fprice+" شيكل");
                                items.add(item);
                            }
                            ItemsSetUp();
                        }
                    }
                });
    }

    private void ItemsSetUp() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsOder.setLayoutManager(linearLayoutManager);

        RecyclerView recyclerView =  binding.itemsOder;
        RecyclerView.Adapter adapter;

        recyclerView.setLayoutManager(linearLayoutManager);

        // Set your adapter and data to the RecyclerView
        adapter = new ConformationItemAdapter(this,items); // Replace 'YourAdapter' and 'data' with your actual adapter and data
        recyclerView.setAdapter(adapter);
    }

    private void bottomNavigationSetUp() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.outline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.love));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.logistics));
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch (model.getId()) {
                    case 1:
                        intent = new Intent(Cart.this, Cart.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(Cart.this, Love.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(Cart.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(Cart.this, OrderActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        Intent intent = new Intent(Cart.this, Setting.class);
                        startActivity(intent);
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                return null;
            }
        });
    }
}