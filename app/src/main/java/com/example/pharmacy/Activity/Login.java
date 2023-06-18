package com.example.pharmacy.Activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.R;

public class Login extends AppCompatActivity {
    private Animation slideDownAnimation;
    private TextView login_title;
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private Switch swRememberMe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Call init() function to initialize all the variables
        init();

        // Call setOnClickListeners() function to set all the onClickListeners
        setOnClickListeners();

        // Call checkRememberMe() function to check if the user has checked the remember me switch
        checkRememberMe();
    }

    private void loadAnimation(){
        // to start the animation for the label
        slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_animation);
        login_title = findViewById(R.id.login_title);
        login_title.setAnimation(slideDownAnimation);
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
