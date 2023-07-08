package com.example.pharmacy.Activity.DoctorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.Customer.OrderActivity;
import com.example.pharmacy.Activity.Customer.ViewOrderDetailsCus;
import com.example.pharmacy.Adaptor.OrderAdapter;
import com.example.pharmacy.Adaptor.OrderStateAdapter;
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

public class OrderStatus extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    Intent intent;
    ListView orderList;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Order> arrayList= new ArrayList<>();
    String path="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        getSupportActionBar().hide();
        bottomNavigationSetUp();
        mAuth = FirebaseAuth.getInstance();
        orderList = findViewById(R.id.list_order_state);
        setUpList();
        //setUpList1();
    }

    private void setUpList1() {
        db.collection("Orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                Log.v("YAhooo", "Number of order documents: " + querySnapshot.size());

                                for (QueryDocumentSnapshot orderDocument : querySnapshot) {
                                    Log.v("YAhooo", "Order document ID: " + orderDocument.getId());

                                    db.collection("Orders").document(orderDocument.getId())
                                            .collection(orderDocument.getId())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> subcollectionTask) {
                                                    if (subcollectionTask.isSuccessful()) {
                                                        QuerySnapshot subcollectionQuerySnapshot = subcollectionTask.getResult();
                                                        if (subcollectionQuerySnapshot != null) {
                                                            Log.v("YAhooo1", "Number of subdocuments: " + subcollectionQuerySnapshot.size());

                                                            for (QueryDocumentSnapshot subdocument : subcollectionQuerySnapshot) {
                                                                Log.v("YAhooo1", "Subdocument ID: " + subdocument.getId());
                                                                // Process the subdocument data here
                                                            }
                                                        } else {
                                                            Log.v("Noooooooooo", "Subcollection query snapshot is null");
                                                        }
                                                    } else {
                                                        Log.v("Noooooooooo", "Failed to retrieve subcollection documents");
                                                    }
                                                }
                                            });
                                }
                            } else {
                                Log.v("Noooooooooo", "Query snapshot is null");
                            }
                        } else {
                            Log.v("Noooooooooo", "Failed to retrieve order documents");
                        }
                    }
                });
    }

    private void setUpList() {
        db.collection("Order")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.v("YAhooo","done");

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.v("YAhooo-------","done-----");
                                db.collection("Orders").document(document.getId()).collection("order")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    Log.v("YAhooo1","done1");
                                                    for (QueryDocumentSnapshot document1 : task.getResult()) {
                                                        db.collection("Orders").document(document.getId()).collection("order").document(document1.getId())
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> subcollectionTask) {
                                                                        if (subcollectionTask.isSuccessful()) {
                                                                            Log.v("YAhooo1","done1");

                                                                            DocumentSnapshot subdocument = subcollectionTask.getResult();
                                                                            String Otitle = subdocument.getString("name");
                                                                            String location = subdocument.getString("location");
                                                                            double price = Double.parseDouble(subdocument.getString("price"));
                                                                            String status = subdocument.getString("status");
                                                                            int postal = Integer.parseInt(subdocument.getString("postal"));
                                                                            if (status.equals("تم الطلب")) {
                                                                                db.collection("Orders").document(document.getId()).collection("order").document(document1.getId()).collection("items")
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<QuerySnapshot> subcollectionTask) {
                                                                                                if (subcollectionTask.isSuccessful()) {
                                                                                                    Log.v("YAhooo3", "done3");

                                                                                                    for (QueryDocumentSnapshot subdocument1 : subcollectionTask.getResult()) {
                                                                                                        String title = subdocument1.getString("name");
                                                                                                        String description = subdocument1.getString("description");
                                                                                                        String pic = subdocument1.getString("image");
                                                                                                        String price = subdocument1.getString("price");
                                                                                                        int quantity = Integer.parseInt(subdocument1.getString("size"));
                                                                                                        String category = subdocument1.getString("category");
                                                                                                        int numberOfItem = Integer.parseInt(subdocument1.getString("numberOfItem"));
                                                                                                        Item item = new Item(title, description, pic, price, quantity, category, numberOfItem);
                                                                                                        items.add(item);
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                Order order = new Order(items, price, Otitle, location, postal, status);
                                                                                path = "Orders/" + document.getId() + "/" + "order" + "/" + document1.getId();
                                                                                arrayList.add(order);
                                                                            }
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
                                                                                intent.putExtra("path",path);
                                                                                startActivity(intent);

                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                    }
                                                }else {
                                                    Log.v("sadsadsadas","Noooo");
                                                }
                                            }
                                        });
                            }
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