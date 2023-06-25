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
import com.example.pharmacy.Activity.ViewItem;
import com.example.pharmacy.Adaptor.OrderAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Order;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainDelivery extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    MeowBottomNavigation bottomNavigation;

    Intent intent;

    ListView orderList;

    ShapeableImageView pofile;
    private FirebaseAuth mAuth;
    private TextView profileHello,profileLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_delivery);
        mAuth = FirebaseAuth.getInstance();
        pofile = findViewById(R.id.userImg);
        profileHello = findViewById(R.id.profileHello);
        profileLoc = findViewById(R.id.userLoc);
        getSupportActionBar().hide();
        bottomNavigationSetUp();
        loadProfileInformation();
        orderList = findViewById(R.id.delivary_order_list);
        setUpList();
    }


    private void setUpList() {
        ArrayList<Order> arrayList= new ArrayList<>();
        while (arrayList.size()<10){
            arrayList.add(new Order(1, "طلب رقم "+1,2, new ArrayList<>(),50.5));
        }
        OrderAdapter adapter = new OrderAdapter(this, 0,arrayList);
        orderList.setAdapter(adapter);

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // code to execute when a list item is clicked
                // position indicates the position of the clicked item in the list
                intent = new Intent(MainDelivery.this, ViewItem.class);
                startActivity(intent);
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
                        .into(pofile);
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