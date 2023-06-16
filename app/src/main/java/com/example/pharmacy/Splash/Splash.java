package com.example.pharmacy.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.Activity.MainActivity;
import com.example.pharmacy.R;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =  new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}
