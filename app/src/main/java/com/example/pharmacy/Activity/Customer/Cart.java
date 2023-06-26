package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Adaptor.ConformationItemAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityCartBinding;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Cart extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    Intent intent;

    ActivityCartBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        bottomNavigationSetUp();

        //TODO: give cart Array from database
        //String s = getIntent().getStringExtra("order");
        //Log.v("S = ", s);
        //Gson gson = new Gson();
        //Order o = gson.fromJson(s, Order.class);
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsOder.setLayoutManager(linearLayoutManager);

        ConformationItemAdapter adapter = new ConformationItemAdapter(this, items);
        binding.itemsOder.setAdapter(adapter);


        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Cart.this,Shipment.class);
                startActivity(intent);
            }
        });


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