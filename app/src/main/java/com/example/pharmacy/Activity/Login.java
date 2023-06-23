package com.example.pharmacy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.Activity.Customer.MainActivity;
import com.example.pharmacy.Activity.DeliveryActivities.MainDelivery;
import com.example.pharmacy.Activity.DoctorActivities.MainActivityDoctor;
import com.example.pharmacy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {
    private TextView login_title;
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private Switch swRememberMe;
    SharedPreferences sharedPreferences;

    DocumentReference mDocRef = FirebaseFirestore.getInstance().document("pharmacy/patient");
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
        //todo Check if the user has checked the remember me switch
        if(editEmail.getText().toString().isEmpty()|| editPassword.getText().toString().isEmpty()){
            CharSequence text = "You should enter email and password!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            return;
        }
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            Map<String, Object> dataToSave = new HashMap<>();
            dataToSave.put("email",email);
            dataToSave.put("password",password);

            mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.v("login", "login success");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("login", "login Fail");
                }
            });

    }

    //todo Create saveUser() function to save the user data
    private void saveUser() {
        //todo Create an object of the User class
        //todo Set the user data to the object
        //todo Convert the object to a JSON string
        //todo Save the JSON string to the shared preferences
        Gson gson = new Gson();
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // editor.putString("sets", gson.toJson(   ));
        // editor.commit();

        //String s = sharedPreferences.getString("sets",null);

        //list = gson.fromJson(s,SetModel[].class);
    }

}
