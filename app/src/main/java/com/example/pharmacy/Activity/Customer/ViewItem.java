package com.example.pharmacy.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ViewItem extends AppCompatActivity {

    CardView back_btn;
    CardView love_btn, plus_btn, minus_btn;
    ImageView img;
    Button add_toCart;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView view_dis, price_view, item_name, number_pics, drug_num;
    ImageView item_img;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        // 1. Initialize the UI components
        initializeUI();

        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drug_num.setText((Integer.parseInt(drug_num.getText() + "") + 1) + "");
            }
        });
        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drug_num.setText(Integer.parseInt(drug_num.getText() + "") - 1);
            }
        });

        String itemJson = getIntent().getStringExtra("item_to_view");

        add_toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Gson().fromJson(itemJson, Item.class);
                Map<String, String> dataToSave = new HashMap<>();
                dataToSave.put("name", item.getTitle());
                dataToSave.put("category", item.getCategory());
                dataToSave.put("description", item.getDiscreption());
                dataToSave.put("price", item.getPrice());
                dataToSave.put("size", item.getQuantity() + "");
                dataToSave.put("image", item.getPic());
                dataToSave.put("numberOfItem", drug_num.getText() + "");

                db.collection("Users").document(mAuth.getUid()).collection("Cart")
                        .add(dataToSave).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast toast = Toast.makeText(ViewItem.this, "تم اضافة المنتج الى المفضلة 🥳", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("خطأ :", e.toString());
                                Toast toast = Toast.makeText(ViewItem.this, "خطأ في اضافة المنتج الى المفضلة 😥", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
            }
        });

        // Check if the itemJson string is not null
        if (itemJson != null && !itemJson.isEmpty()) {
            Item item = new Gson().fromJson(itemJson, Item.class);

            if (item != null) {

                view_dis.setText(item.getDiscreption());
                price_view.setText(item.getPrice() + " شيكل");
                item_name.setText(item.getTitle());
                number_pics.setText(item.getQuantity() + "");
                Glide.with(this)
                        .load(item.getPic())
                        .into(item_img);
                //TODO: make heart fill if its in fav list
/*
                db.collection("Users").document(mAuth.getUid()).collection("Favorite")
                        .whereEqualTo("name",item.getTitle()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document != null)
                                            img.setImageDrawable(getDrawable(R.drawable.fill_heart));
                                        else {
                                            img.setImageDrawable(getDrawable(R.drawable.unfilled_heart));

                                        }
                                    }

                                }
                            }
                        });

*/
            }
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewItem.this, MainActivity.class);
                startActivity(intent);
            }
        });

        love_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO: add to love list
                Item item = new Gson().fromJson(itemJson, Item.class);
                Map<String, String> dataToSave = new HashMap<>();
                dataToSave.put("name", item.getTitle());
                dataToSave.put("category", item.getCategory());
                dataToSave.put("description", item.getDiscreption());
                dataToSave.put("price", item.getPrice());
                dataToSave.put("size", item.getQuantity() + "");
                dataToSave.put("image", item.getPic());

                db.collection("Users").document(mAuth.getUid()).collection("Favorite")
                        .add(dataToSave).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast toast = Toast.makeText(ViewItem.this, "تم اضافة المنتج الى المفضلة 🥳", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("خطأ :", e.toString());
                                Toast toast = Toast.makeText(ViewItem.this, "خطأ في اضافة المنتج الى المفضلة 😥", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });

                img.setImageDrawable(getDrawable(R.drawable.fill_heart));
                //  img.setImageDrawable(getDrawable(R.drawable.unfilled_heart));

            }
        });


    }

    private void initializeUI() {
        back_btn = findViewById(R.id.backview_btn);
        love_btn = findViewById(R.id.loveview_btn);
        add_toCart = findViewById(R.id.add_tocart);
        view_dis = findViewById(R.id.view_dis);
        price_view = findViewById(R.id.price_view);
        item_img = findViewById(R.id.item_img);
        item_name = findViewById(R.id.item_name);
        number_pics = findViewById(R.id.number_pics);
        img = findViewById(R.id.love_img);
        drug_num = findViewById(R.id.drug_num);
        plus_btn = findViewById(R.id.plus_btn);
        minus_btn = findViewById(R.id.minus_btn);
    }
}