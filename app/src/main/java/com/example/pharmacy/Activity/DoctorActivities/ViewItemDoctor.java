package com.example.pharmacy.Activity.DoctorActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

public class ViewItemDoctor extends AppCompatActivity {

    CardView back_btn;
    TextView view_dis,price_view,item_name;
    ImageView item_img;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item_doctor);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        back_btn=findViewById(R.id.backview_btn);

        view_dis = findViewById(R.id.view_dis);
        price_view = findViewById(R.id.price_view);
        item_img = findViewById(R.id.item_img);
        item_name = findViewById(R.id.item_name);

        String itemJson = getIntent().getStringExtra("item_to_view");

        // Check if the itemJson string is not null
        if (itemJson != null && !itemJson.isEmpty()) {
            Item item = new Gson().fromJson(itemJson, Item.class);

            if(item != null){

                view_dis.setText(item.getDiscreption());
                price_view.setText(item.getPrice()+" شيكل");
                item_name.setText(item.getTitle());
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
                Intent intent = new Intent(ViewItemDoctor.this, MainActivityDoctor.class);
                startActivity(intent);
            }
        });

    }
}