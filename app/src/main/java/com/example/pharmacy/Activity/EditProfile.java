package com.example.pharmacy.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfile extends AppCompatActivity {

    private Button save;
    private ImageButton image;

    private ImageView back;
    private TextInputEditText name, email, phone, password, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        // initialize the button
        intialize();

        // set on click listener
        setOnClick();
    }
    // initialize the edit text
    private void intialize(){
        image = findViewById(R.id.imageButton2);
        save = findViewById(R.id.btn_save);
        back = findViewById(R.id.iv_backArrow);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        phone = findViewById(R.id.editText_phone);
        address = findViewById(R.id.editText_adress);
    }

    // set on click listener
    private void setOnClick(){
        save.setOnClickListener(v -> {
            //todo: save the data
        });
        back.setOnClickListener(v -> {
            //todo: go back
        });
        image.setOnClickListener(v -> {
            //todo: change the image
        });
    }
}
