package com.example.pharmacy.Activity.DeliveryActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.Customer.Setting;
import com.example.pharmacy.Activity.DeliveryActivities.MainDelivery;
import com.example.pharmacy.Activity.Login;
import com.example.pharmacy.Activity.Profile;
import com.example.pharmacy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SettingDelivery extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    Intent intent;

    CardView user,setting;

    Button logout;
    Switch nightModeSwitch ;
    private TextView profNameSettings;
    private ImageView userImg;

    private boolean isUserInteracting = false;
    private static final String STATE_USER_INTERACTING = "userInteracting";
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_delivery);
        getSupportActionBar().hide();
        bottomNavigationSetUp();

        setting = findViewById(R.id.setting_card);
        user = findViewById(R.id.user_card);
        logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        profNameSettings = findViewById(R.id.profNameSettings);
        userImg = findViewById(R.id.userImg);
        loadProfileInformation();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingDelivery.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingDelivery.this,SettingDelivery.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingDelivery.this, Profile.class);
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
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(SettingDelivery.this, SettingDelivery.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(SettingDelivery.this, MainDelivery.class);
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
    private void loadProfileInformation(){
        DocumentReference docRef = db.collection("Users").document(mAuth.getUid());

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null){
            if(user.getPhotoUrl() != null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(userImg);
            }
            if (user.getDisplayName() != null){
                profNameSettings.setText( user.getDisplayName());
            }
        }
    }
}