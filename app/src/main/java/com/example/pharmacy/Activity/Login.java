package com.example.pharmacy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
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

import com.bumptech.glide.Glide;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hide the action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Call loadAnimation() function to load the animation for the label
        loadAnimation();

        // Call init() function to initialize all the variables
        init();

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("myPharmacyEmail","");
        String password = sharedPreferences.getString("myPharmacyPassword","");

        if(!email.isEmpty() || !password.isEmpty()) {
            editEmail.setText(email);
            editPassword.setText(password);
        }

        // Call setOnClickListeners() function to set all the onClickListeners
        setOnClickListeners();

        // Call checkRememberMe() function to check if the user has checked the remember me switch
        checkRememberMe();
    }

    private void loadAnimation(){
        // to start the animation for the label
        login_title = findViewById(R.id.login_title);
        ImageView login_img = findViewById(R.id.login_img);

        // to start the animation for the label
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
            // to hide the keyboard when the user clicks on the login button
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(btnLogin.getWindowToken(), 0);
            login();
        });
    }

    private void checkRememberMe() {
        if(swRememberMe.isChecked()) {
            saveUser();
        }
    }

    // to login the user
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
                    DocumentReference docRef = db.collection("Users").document(mAuth.getUid());

                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                if(documentSnapshot.getString("role").equals("doctor")){
                                    Intent intent = new Intent(Login.this,MainActivityDoctor.class);
                                    startActivity(intent);
                                    finish();
                                } else if (documentSnapshot.getString("role").equals("patient")) {
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else if (documentSnapshot.getString("role").equals("delivery")) {
                                    Intent intent = new Intent(Login.this,MainDelivery.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }else
                                Toast.makeText(getApplicationContext(),"خطأ في جلب المعلومات" , Toast.LENGTH_SHORT);
                        }
                    });

                }else{
                    CharSequence text = "هنالك خطأ في تسجيل الدخول تأكد من البريد وكلمة المرور";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(Login.this, text, duration);
                    toast.show();
                }
            }
        });
        // to check if the user has checked the remember me switch
        checkRememberMe();

    }

    // to save the user email and password in the shared preferences
    private void saveUser() {
        Gson gson = new Gson();
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("myPharmacyEmail", gson.toJson(editEmail.getText().toString()));
        editor.putString("myPharmacyPassword", gson.toJson(editPassword.getText().toString()));
        editor.commit();
    }

}
