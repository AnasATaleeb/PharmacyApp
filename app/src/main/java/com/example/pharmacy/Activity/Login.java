package com.example.pharmacy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.Activity.Customer.MainActivity;
import com.example.pharmacy.Activity.DeliveryActivities.MainDelivery;
import com.example.pharmacy.Activity.DoctorActivities.MainActivityDoctor;
import com.example.pharmacy.R;

public class Login extends AppCompatActivity {
    private TextView login_title;
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private Switch swRememberMe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        loadAnimation();
        // Call init() function to initialize all the variables
        init();

        // Call setOnClickListeners() function to set all the onClickListeners
        setOnClickListeners();

        // Call checkRememberMe() function to check if the user has checked the remember me switch
        checkRememberMe();
    }

    private void loadAnimation(){
        // to start the animation for the label
        login_title = findViewById(R.id.login_title);
        ImageView login_img = findViewById(R.id.login_img);

        Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        login_title.setAnimation(slideDownAnimation);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);
        login_img.setAnimation(fadeIn);
    }
    private void init() {
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        swRememberMe = findViewById(R.id.swRememberMe);
    }

    private void setOnClickListeners() {
        btnLogin.setOnClickListener(v -> {
            String mail = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            //todo Call login() function to login the user
            login();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void checkRememberMe() {
        //todo Check if the user has checked the remember me switch
        if(swRememberMe.isChecked()) {
            //todo Call saveUser() function to save the user data
            saveUser();
        }
    }

    //todo Create login() function to login the user
    private void login() {
        //todo Check if the user has entered the email and password
        //todo Check if the user has entered a valid email
        //todo Check if the user has entered a valid password
        //todo Check if the user has checked the remember me switch
    }

    //todo Create saveUser() function to save the user data
    private void saveUser() {
        //todo Create an object of the User class
        //todo Set the user data to the object
        //todo Convert the object to a JSON string
        //todo Save the JSON string to the shared preferences
    }

}
