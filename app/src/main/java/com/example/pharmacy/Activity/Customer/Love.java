package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Adaptor.LoveAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
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

public class Love extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MeowBottomNavigation bottomNavigation;
    Intent intent;

    ListView loveList;
    ArrayList<Item> arrayList;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        getSupportActionBar().hide();
        bottomNavigationSetUp();
        mAuth = FirebaseAuth.getInstance();
        loveList = findViewById(R.id.love_list);
        setUpList();

    }

    private void setUpList() {
        arrayList = new ArrayList<>();

        db.collection("Users").document(mAuth.getUid()).collection("Favorite")
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

                                Item item = new Item(title, description, pic, price, quantity, category);
                                arrayList.add(item);
                            }
                            LoveAdapter adapter = new LoveAdapter(Love.this, 0,arrayList);
                            loveList.setAdapter(adapter);

                            loveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(Love.this, ViewItem.class);

                                    Item model = arrayList.get(position);
                                    if (model != null) {
                                        String itemJson = new Gson().toJson(model);
                                        intent.putExtra("item_to_view", itemJson);
                                    }
                                    startActivity(intent);
                                }
                            });
                        }
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