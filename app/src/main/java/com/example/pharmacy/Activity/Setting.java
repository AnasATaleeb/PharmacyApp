package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.R;

import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Setting extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    Intent intent;

    Switch nightModeSwitch ;
    private boolean isUserInteracting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();
        bottomNavigationSetUp();
        nightModeSwitch = findViewById(R.id.switch2);

        // Check the current Night Mode state and set the switch accordingly
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // This change is not due to user interaction
        isUserInteracting = false;

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                nightModeSwitch.setChecked(true);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                nightModeSwitch.setChecked(false);
                break;
        }

        // Allow changes due to user interaction again
        isUserInteracting = true;

        // Set the listener
        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isUserInteracting) {
                    if(isChecked) {
                        // If the switch is checked, set the Night Mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        // If the switch is unchecked, set the Light Mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
            }
        });
    }

    private void bottomNavigationSetUp() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.outline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.outline_shopping_bag_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.love));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.logistics));
        bottomNavigation.show(5, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(Setting.this, Cart.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(Setting.this, Love.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(Setting.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(Setting.this, Order.class);
                        startActivity(intent);
                        break;

                    case 5:
                        Intent intent = new Intent(Setting.this, Setting.class);
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