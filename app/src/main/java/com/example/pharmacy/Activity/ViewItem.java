package com.example.pharmacy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pharmacy.Activity.Customer.MainActivity;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ViewItem extends AppCompatActivity {

    CardView back_btn;
    CardView love_btn;
    ImageView img;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView view_dis,price_view,item_name,number_pics;
    ImageView item_img;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        back_btn=findViewById(R.id.backview_btn);
        love_btn = findViewById(R.id.loveview_btn);

        view_dis = findViewById(R.id.view_dis);
        price_view = findViewById(R.id.price_view);
        item_img = findViewById(R.id.item_img);
        item_name = findViewById(R.id.item_name);
        number_pics = findViewById(R.id.number_pics);
        img = findViewById(R.id.love_img);



        String itemJson = getIntent().getStringExtra("item_to_view");

        // Check if the itemJson string is not null
        if (itemJson != null && !itemJson.isEmpty()) {
            Item item = new Gson().fromJson(itemJson, Item.class);

            if(item != null){

                view_dis.setText(item.getDiscreption());
                price_view.setText(item.getPrice()+" شيكل");
                item_name.setText(item.getTitle());
                number_pics.setText(item.getQuantity()+"");
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
                                    if (task.getResult().size() >1)
                                        img.setImageDrawable(getDrawable(R.drawable.fill_heart));
                                    else {
                                        img.setImageDrawable(getDrawable(R.drawable.unfilled_heart));

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
                dataToSave.put("name",item.getTitle());
                dataToSave.put("category", item.getCategory());
                dataToSave.put("description",item.getDiscreption());
                dataToSave.put("price",item.getPrice());
                dataToSave.put("size",item.getQuantity()+"");
                dataToSave.put("image",item.getPic());

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
}