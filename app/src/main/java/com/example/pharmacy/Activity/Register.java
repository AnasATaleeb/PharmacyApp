package com.example.pharmacy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pharmacy.R;
import com.example.pharmacy.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private ShapeableImageView userImg;
    private TextInputEditText userName;
    private TextInputEditText userEmail;
    private TextInputEditText userPhone;
    private TextInputEditText userLocation;
    private TextInputEditText userPassword;

    private RadioGroup group;

    private Button register;

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;

    DocumentReference mDocRef = FirebaseFirestore.getInstance().document("pharmacy/patient");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        initializeData();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        if(userPassword.getText().toString().isEmpty() || userEmail.getText().toString().isEmpty()||
        userLocation.getText().toString().isEmpty()|| userPhone.getText().toString().isEmpty()||
        userName.getText().toString().isEmpty()){
            CharSequence text = "الرجاء تعبأة جميع الفراغات";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            return;
        }
        if(group.getCheckedRadioButtonId() ==-1){
            CharSequence text = "لازم تختار نوع الحساب يا وردة 😊";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        final String value =
                ((RadioButton)findViewById(group.getCheckedRadioButtonId()))
                        .getText().toString();
        if(group.getCheckedRadioButtonId() ==-1){
            CharSequence text = "يجب اختيار نوع الحساب 😊";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(Register.this, text, duration);
            toast.show();
        }
        if(value.equals("دكتور")){

        }
        else if (value.equals("عميل")){
            ImageView image = (ImageView) userImg;
            Patient patient = new Patient(userName.getText().toString(), userPhone.getText().toString(),
                    userEmail.getText().toString(),userLocation.getText().toString());

            String email =userEmail.getText().toString();
            String password =userPassword.getText().toString();

            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                CharSequence text = "الايميل صحيح 😎";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(Register.this, text, duration);
                toast.show();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            CharSequence text = "تم تسجيلك بنجاح 🥳";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(Register.this, text, duration);
                            toast.show();

                            Map<String, Object> dataToSave = new HashMap<>();
                            dataToSave.put("Customer",patient);

                            mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast toast = Toast.makeText(Register.this, "تم انشاء حسابك بنجاح 🥳", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intent = new Intent(Register.this,MainSign.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast toast = Toast.makeText(Register.this, "خطأ في انشاء الحساب 😥", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                        }else{
                            CharSequence text = "الايميل مسجل بحساب موجود بالفعل حاول بحساب آخر 🥰";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(Register.this, text, duration);
                            toast.show();
                        }
                    }
                });


            }else {
                CharSequence text = "الرجاء ادخال ايميل صالح 👀";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(Register.this, text, duration);
                toast.show();
            }

        }
        else if (value.equals("موظف توصيل")) {

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
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }
}