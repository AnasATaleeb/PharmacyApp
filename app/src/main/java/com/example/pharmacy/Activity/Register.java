package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pharmacy.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {

    private ShapeableImageView userImg;
    private TextInputEditText userName;
    private TextInputEditText userEmail;
    private TextInputEditText userPhone;
    private TextInputEditText userLocation;
    private TextInputEditText userPassword;

    private RadioGroup group;

    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeData();
        registerUser();
    }

    private void registerUser() {
        if(userPassword.getText().toString().isEmpty() || userEmail.getText().toString().isEmpty()||
        userLocation.getText().toString().isEmpty()|| userPhone.getText().toString().isEmpty()||
        userName.getText().toString().isEmpty()){
            CharSequence text = "You should fill all boxes !";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
    }

    private void initializeData() {
        userImg = findViewById(R.id.userImg);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        userLocation = findViewById(R.id.userLocation);
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        register = findViewById(R.id.btnRegister);
        group = findViewById(R.id.radioGroup);
    }
}