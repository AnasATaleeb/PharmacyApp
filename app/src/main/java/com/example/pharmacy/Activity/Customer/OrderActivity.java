package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Adaptor.OrderAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.gson.Gson;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class OrderActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    Intent intent;
    ListView orderList;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().hide();
        bottomNavigationSetUp();
        orderList = findViewById(R.id.list_order_state);
        setUpList();
    }

    private void setUpList() {
        ArrayList<Order> arrayList= new ArrayList<>();
        while (arrayList.size()<10){
            //TODO: get orders from data base
            ArrayList<Item> items = new ArrayList<>();
            items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,24.5,7));
            items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,23.38,1));
            items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,65.2,132));
            items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,55.1,13));
            items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,89.5,10));

            Order o = (new Order(1,"طلب رقم #1", 1, items, 67.5));
            arrayList.add(o);
        }
        OrderAdapter adapter = new OrderAdapter(this, 0,arrayList);
        orderList.setAdapter(adapter);

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // code to execute when a list item is clicked
                // position indicates the position of the clicked item in the list
                intent = new Intent(OrderActivity.this, ViewOrderDetailsCus.class);
                Gson gson = new Gson();
                intent.putExtra("order",gson.toJson(arrayList.get(position)));
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
        bottomNavigation.show(4, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(OrderActivity.this, Cart.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(OrderActivity.this, Love.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(OrderActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(OrderActivity.this, OrderActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        Intent intent = new Intent(OrderActivity.this, Setting.class);
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