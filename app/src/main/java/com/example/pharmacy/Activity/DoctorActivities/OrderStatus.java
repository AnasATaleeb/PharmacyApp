package com.example.pharmacy.Activity.DoctorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Adaptor.OrderAdapter;
import com.example.pharmacy.Adaptor.OrderStateAdapter;
import com.example.pharmacy.R;
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

public class OrderStatus extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    Intent intent;
    ListView orderList;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Order> arrayList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        getSupportActionBar().hide();
        bottomNavigationSetUp();
        mAuth = FirebaseAuth.getInstance();
        orderList = findViewById(R.id.list_order_state);
        setUpList();

    }

    private void setUpList() {
        //TODO: get orders from data base
        db.collection("Orders").document("Order").collection(mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Otitle = document.getString("name");
                                String location = document.getString("location");
                                double price = Double.parseDouble(document.getString("price"));
                                String status = document.getString("status");
                                int postal = Integer.parseInt(document.getString("postal"));
                                //get collection "items" into this doc
                                db.collection("Orders").document("Order").collection(mAuth.getUid())
                                        .document(document.getId()).collection("items")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> subcollectionTask) {
                                                if (subcollectionTask.isSuccessful()) {
                                                    for (QueryDocumentSnapshot subdocument : subcollectionTask.getResult()) {
                                                        String title = subdocument.getString("name");
                                                        String description = subdocument.getString("description");
                                                        String pic = subdocument.getString("image");
                                                        String price = subdocument.getString("price");
                                                        int quantity = Integer.parseInt(subdocument.getString("size"));
                                                        String category = subdocument.getString("category");
                                                        int numberOfItem = Integer.parseInt(subdocument.getString("numberOfItem"));
                                                        Item item = new Item(title, description, pic, price, quantity, category,numberOfItem);
                                                        items.add(item);
                                                    }
                                                }
                                            }
                                        });

                                Order order = new Order(items,price,Otitle, location, postal, status);
                                arrayList.add(order);
                            }
                            OrderStateAdapter adapter = new OrderStateAdapter(OrderStatus.this, 0,arrayList);
                            orderList.setAdapter(adapter);

                            orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // code to execute when a list item is clicked
                                    // position indicates the position of the clicked item in the list
                                    intent = new Intent(OrderStatus.this, ViewOrderDetails.class);
                                    Gson gson = new Gson();
                                    intent.putExtra("order",gson.toJson(arrayList.get(position)));
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }

    private void bottomNavigationSetUp() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.outline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.tracking));
        bottomNavigation.show(3, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(OrderStatus.this, SettingDoctors.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(OrderStatus.this, MainActivityDoctor.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(OrderStatus.this, OrderStatus.class);
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