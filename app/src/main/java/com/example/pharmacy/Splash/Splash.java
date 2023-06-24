package com.example.pharmacy.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.Activity.Customer.searchItem;
import com.example.pharmacy.Activity.MainSign;
import com.example.pharmacy.R;

public class Splash extends AppCompatActivity {
    private Animation bounceAnime;
    private TextView label;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        // to start the animation for "world explorer" label
        bounceAnime = AnimationUtils.loadAnimation(this, R.anim.bounce_animation);
        label = findViewById(R.id.label);
        label.setAnimation(bounceAnime);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(Splash.this, MainSign.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}
