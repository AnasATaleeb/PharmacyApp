package com.example.pharmacy.Activity.Customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.Login;
import com.example.pharmacy.Activity.Profile;
import com.example.pharmacy.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Setting extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    Intent intent;

    CardView user, setting, love, cart;
    private TextView profNameSettings;
    private ImageView userImg;

    Button logout;
    Switch nightModeSwitch;
    private boolean isUserInteracting = false;
    private static final String STATE_USER_INTERACTING = "userInteracting";
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Hide the action bar
        getSupportActionBar().hide();

        bottomNavigationSetUp();

        // initializeUI
        initializeUI();

        loadProfileInformation();

        // setListeners
        setListeners();

        // nightModeSwitch
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
            Intent i = new Intent(getApplicationContext(), Setting.class);
            startActivity(i);
            finish();
        });
    }

    private void initializeUI() {
        mAuth = FirebaseAuth.getInstance();
        setting = findViewById(R.id.setting_card);
        user = findViewById(R.id.user_card);
        love = findViewById(R.id.love_card);
        cart = findViewById(R.id.cart_card);
        logout = findViewById(R.id.logout);
        profNameSettings = findViewById(R.id.profNameSettings);
        userImg = findViewById(R.id.userImg);
        nightModeSwitch = findViewById(R.id.switch2);

    }

    private void setListeners() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Setting.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Setting.this, Setting.class);
                startActivity(intent);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Setting.this, Profile.class);
                startActivity(intent);
            }
        });

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Setting.this, Love.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Setting.this, Cart.class);
                startActivity(intent);
            }
        });
    }

    private void bottomNavigationSetUp() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.outline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.home));
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
                        finish();
                        break;

                    case 2:
                        intent = new Intent(Setting.this, Love.class);
                        startActivity(intent);
                        finish();
                        break;

                    case 3:
                        intent = new Intent(Setting.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case 4:
                        intent = new Intent(Setting.this, OrderActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case 5:
                        Intent intent = new Intent(Setting.this, Setting.class);
                        startActivity(intent);
                        finish();
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