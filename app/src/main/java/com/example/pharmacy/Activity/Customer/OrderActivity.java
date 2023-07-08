package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.DoctorActivities.ViewOrderDetails;
import com.example.pharmacy.Adaptor.OrderAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class OrderActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    Intent intent;
    ListView orderList;

    private FirebaseAuth mAuth;
    ArrayList<Order> arrayList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Item> items = new ArrayList<>();
    Button talab,tawseel,irsal;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        bottomNavigationSetUp();
        orderList = findViewById(R.id.list_order_state);

        talab = findViewById(R.id.talab);
        tawseel = findViewById(R.id.tawseel);
        irsal = findViewById(R.id.irsal);

        setUpListTamTalab();
        tawseel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpListTamTawseel();
            }
        });
        talab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpListTamTalab();
            }
        });

        irsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpListTamIrsalTalab();
            }
        });



    }

    private void setUpListTamTalab() {
        arrayList= new ArrayList<>();
        db.collection("Orders").document(mAuth.getUid()).collection("order")
                    .whereEqualTo("status", "تم الطلب")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    String Otitle = document.getString("name");
                                String location = document.getString("location");
                                if (document.getString("price") == null)
                                    return;
                                double price = Double.parseDouble(document.getString("price"));
                                String status = document.getString("status");
                                int postal = Integer.parseInt(document.getString("postal"));
                                String key = document.getString("key");
                                //get collection "items" into this doc
                                db.collection("Orders").document(mAuth.getUid()).collection("order").document(document.getId()).collection("items")
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
                                                        Item item = new Item(title, description, pic, price, quantity, category, numberOfItem);
                                                        items.add(item);
                                                    }
                                                }
                                            }
                                        });
                                Order order = new Order(items, price, Otitle, location, postal, status);
                                arrayList.add(order);
                                OrderAdapter adapter = new OrderAdapter(OrderActivity.this, 0, arrayList);
                                orderList.setAdapter(adapter);
                            }
                        }

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

                    });
    }

    private void setUpListTamIrsalTalab() {
        arrayList= new ArrayList<>();
        db.collection("Orders").document(mAuth.getUid()).collection("order")
                .whereEqualTo("status", "تم ارسال الطلب")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String Otitle = document.getString("name");
                                String location = document.getString("location");
                                if (document.getString("price") == null)
                                    return;
                                double price = Double.parseDouble(document.getString("price"));
                                String status = document.getString("status");
                                int postal = Integer.parseInt(document.getString("postal"));
                                String key = document.getString("key");
                                //get collection "items" into this doc
                                db.collection("Orders").document(mAuth.getUid()).collection("order").document(document.getId()).collection("items")
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
                                                        Item item = new Item(title, description, pic, price, quantity, category, numberOfItem);
                                                        items.add(item);
                                                    }
                                                }
                                            }
                                        });
                                Order order = new Order(items, price, Otitle, location, postal, status);
                                arrayList.add(order);
                                OrderAdapter adapter = new OrderAdapter(OrderActivity.this, 0, arrayList);
                                orderList.setAdapter(adapter);
                            }
                        }

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

                });
    }

    private void setUpListTamTawseel() {
        arrayList= new ArrayList<>();
        db.collection("Orders").document(mAuth.getUid()).collection("order")
                .whereEqualTo("status", "تم ارسال الطلب")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String Otitle = document.getString("name");
                                String location = document.getString("location");
                                if (document.getString("price") == null)
                                    return;
                                double price = Double.parseDouble(document.getString("price"));
                                String status = document.getString("status");
                                int postal = Integer.parseInt(document.getString("postal"));
                                String key = document.getString("key");
                                //get collection "items" into this doc
                                db.collection("Orders").document(mAuth.getUid()).collection("order").document(document.getId()).collection("items")
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
                                                        Item item = new Item(title, description, pic, price, quantity, category, numberOfItem);
                                                        items.add(item);
                                                    }
                                                }
                                            }
                                        });
                                Order order = new Order(items, price, Otitle, location, postal, status);
                                arrayList.add(order);
                                OrderAdapter adapter = new OrderAdapter(OrderActivity.this, 0, arrayList);
                                orderList.setAdapter(adapter);
                            }
                        }

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