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
import android.widget.ProgressBar;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        loadAnimation();
        // Call init() function to initialize all the variables
        init();

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("myPharmacyEmail","");
        String password = sharedPreferences.getString("myPharmacyPassword","");

        editEmail.setText(email.substring(1,email.length()-1));
        editPassword.setText(password.substring(1,password.length()-1));
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
            login();
        });
    }

    private void checkRememberMe() {
        if(swRememberMe.isChecked()) {
            saveUser();
        }
    }

    private void login() {
        if(editEmail.getText().toString().isEmpty()|| editPassword.getText().toString().isEmpty()){
            CharSequence text = "يجب ادخال الايميل وكلمة المرور 🤗";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            return;
        }

        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    CharSequence text = "تم تسجيل الدخول بنجاح 🥳";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(Login.this, text, duration);
                    toast.show();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    CharSequence text = "هنالك خطأ في تسجيل الدخول تأكد من البريد وكلمة المرور";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(Login.this, text, duration);
                    toast.show();
                }
            }
        });
        checkRememberMe();

    }

    private void saveUser() {
        Gson gson = new Gson();
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("myPharmacyEmail", gson.toJson(editEmail.getText()));
        editor.putString("myPharmacyPassword", gson.toJson(editPassword.getText()));
        editor.commit();
    }

}
