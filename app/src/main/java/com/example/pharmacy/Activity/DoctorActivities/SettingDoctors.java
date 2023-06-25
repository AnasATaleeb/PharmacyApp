package com.example.pharmacy.Activity.DoctorActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.DeliveryActivities.SettingDelivery;
import com.example.pharmacy.Activity.Login;
import com.example.pharmacy.Activity.Profile;
import com.example.pharmacy.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SettingDoctors extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    Intent intent;

    Button logout;
    CardView user,setting,orders;
    Switch nightModeSwitch ;
    private boolean isUserInteracting = false;
    private static final String STATE_USER_INTERACTING = "userInteracting";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_doctors);
        getSupportActionBar().hide();
        bottomNavigationSetUp();

        setting = findViewById(R.id.setting_card);
        user = findViewById(R.id.user_card);
        orders= findViewById(R.id.orders);
        logout = findViewById(R.id.logout);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingDoctors.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingDoctors.this,SettingDelivery.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingDoctors.this, Profile.class);
                startActivity(intent);
            }
        });


        nightModeSwitch = findViewById(R.id.switch2);

        SharedPreferences sharedPref = getSharedPreferences("nightModePref", MODE_PRIVATE);
        boolean isNightModeOn = sharedPref.getBoolean("NightMode", false);

        if (isNightModeOn) {
            nightModeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            nightModeSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            if (isChecked) {
                editor.putBoolean("NightMode", true);
            } else {
                editor.putBoolean("NightMode", false);
            }
            editor.apply();

            // restart app
            Intent i = new Intent(getApplicationContext(), SettingDelivery.class);
            startActivity(i);
            finish();
        });
    }

    private void bottomNavigationSetUp() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.outline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.tracking));
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(SettingDoctors.this, SettingDoctors.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(SettingDoctors.this, MainActivityDoctor.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(SettingDoctors.this, OrderStatus.class);
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