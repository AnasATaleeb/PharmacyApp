package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pharmacy.Activity.Customer.MainActivity;
import com.example.pharmacy.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    CardView cardView;
    ShapeableImageView pofile;
    private FirebaseAuth mAuth;
    TextView profName,profEmail,profPhone,profLocation;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        cardView = findViewById(R.id.add_Item);
        pofile = findViewById(R.id.userImg);
        mAuth = FirebaseAuth.getInstance();
        profName = findViewById(R.id.profName);
        profEmail = findViewById(R.id.profEmail);
        profPhone = findViewById(R.id.profPhone);
        profLocation = findViewById(R.id.profLocation);

        loadProfileInformation();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadProfileInformation(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null){
            if(user.getPhotoUrl() != null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(pofile);
            }
            if (user.getDisplayName() != null){
                profName.setText( user.getDisplayName());
            }

            profEmail.setText(user.getEmail());
            profPhone.setText(user.getPhoneNumber());

        }
    }
}