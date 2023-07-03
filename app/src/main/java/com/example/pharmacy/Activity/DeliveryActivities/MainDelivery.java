package com.example.pharmacy.Activity.DeliveryActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.Customer.ViewItem;
import com.example.pharmacy.Activity.DoctorActivities.OrderStatus;
import com.example.pharmacy.Activity.DoctorActivities.ViewOrderDetails;
import com.example.pharmacy.Adaptor.OrderAdapter;
import com.example.pharmacy.Adaptor.OrderStateAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainDelivery extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    MeowBottomNavigation bottomNavigation;

    Intent intent;

    ListView orderList;

    ShapeableImageView profile;
    private FirebaseAuth mAuth;
    private TextView profileHello,profileLoc;
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Order> arrayList= new ArrayList<>();
    String path="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_delivery);
        mAuth = FirebaseAuth.getInstance();
        profile = findViewById(R.id.userImg);
        profileHello = findViewById(R.id.profileHello);
        profileLoc = findViewById(R.id.userLoc);
        getSupportActionBar().hide();
        bottomNavigationSetUp();
        loadProfileInformation();
        orderList = findViewById(R.id.delivary_order_list);
        setUpList();
    }

    private void setUpList() {
        //TODO: get orders from data base

        db.collection("Orders")
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
                                db.collection("Orders").document(document.getId()).collection(document.getString("key"))
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
                                path = "Orders/"+document.getId();
                                arrayList.add(order);
                            }
                            OrderAdapter adapter = new OrderAdapter(MainDelivery.this, 0,arrayList);
                            orderList.setAdapter(adapter);

                            orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // code to execute when a list item is clicked
                                    // position indicates the position of the clicked item in the list
                                    intent = new Intent(MainDelivery.this, DelivaryStateActivity.class);
                                    Gson gson = new Gson();
                                    intent.putExtra("order",gson.toJson(arrayList.get(position)));
                                    intent.putExtra("path",path);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                });
    }

    private void loadProfileInformation(){
        DocumentReference docRef = db.collection("Users").document(mAuth.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    profileLoc.setText(documentSnapshot.getString("location"));
                }else
                    Toast.makeText(getApplicationContext(),"خطأ في جلب المعلومات" , Toast.LENGTH_SHORT);
            }
        });
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null){
            if(user.getPhotoUrl() != null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(profile);
            }
            if (user.getDisplayName() != null){
                profileHello.setText( "مرحبا "+user.getDisplayName());
            }

        }
    }
    private void bottomNavigationSetUp() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.outline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home));
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(MainDelivery.this, SettingDelivery.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(MainDelivery.this, MainDelivery.class);
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