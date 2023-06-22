package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.ViewItem;
import com.example.pharmacy.Adaptor.LoveAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Love extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    Intent intent;

    ListView loveList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        getSupportActionBar().hide();
        bottomNavigationSetUp();

        loveList = findViewById(R.id.love_list);
        setUpList();

    }

    private void setUpList() {
        ArrayList<Item> arrayList= new ArrayList<>();
        while (arrayList.size()<10){
            arrayList.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,25.8));
        }
        LoveAdapter adapter = new LoveAdapter(this, 0,arrayList);
        loveList.setAdapter(adapter);


        loveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // code to execute when a list item is clicked
                // position indicates the position of the clicked item in the list
                intent = new Intent(Love.this, ViewItem.class);
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
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(Love.this, Cart.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(Love.this, Love.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(Love.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(Love.this, OrderActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        Intent intent = new Intent(Love.this, Setting.class);
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