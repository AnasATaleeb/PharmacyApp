package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pharmacy.Activity.Customer.MainActivity;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.google.gson.Gson;

public class ViewItem extends AppCompatActivity {

    CardView back_btn;
    CardView love_btn;

    TextView view_dis,price_view,item_name,number_pics;
    ImageView item_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        getSupportActionBar().hide();

        back_btn=findViewById(R.id.backview_btn);
        love_btn = findViewById(R.id.loveview_btn);

        view_dis = findViewById(R.id.view_dis);
        price_view = findViewById(R.id.price_view);
        item_img = findViewById(R.id.item_img);
        item_name = findViewById(R.id.item_name);
        number_pics = findViewById(R.id.number_pics);

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
                ImageView img = findViewById(R.id.love_img);
                    //TODO: add to love list
                    img.setImageDrawable(getDrawable(R.drawable.fill_heart));
                  //  img.setImageDrawable(getDrawable(R.drawable.unfilled_heart));

            }
        });



    }
}