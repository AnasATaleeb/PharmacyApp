package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pharmacy.Activity.Customer.MainActivity;
import com.example.pharmacy.R;

public class ViewItem extends AppCompatActivity {

    CardView back_btn;
    CardView love_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        getSupportActionBar().hide();

        back_btn=findViewById(R.id.backview_btn);
        love_btn = findViewById(R.id.loveview_btn);

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

                    img.setImageDrawable(getDrawable(R.drawable.fill_heart));
                  //  img.setImageDrawable(getDrawable(R.drawable.unfilled_heart));

            }
        });



    }
}