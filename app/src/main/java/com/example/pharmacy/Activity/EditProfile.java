package com.example.pharmacy.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pharmacy.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfile extends AppCompatActivity {

    private Button save;
    private ImageButton image;

    private ImageView back;
    private TextInputEditText name, email, phone, password, address;
    private ConstraintLayout constraintLayoutmain;

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
    private void intialize() {
        image = findViewById(R.id.imageButton2);
        save = findViewById(R.id.btn_save);
        back = findViewById(R.id.iv_backArrow);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        phone = findViewById(R.id.editText_phone);
        address = findViewById(R.id.editText_adress);
        constraintLayoutmain = findViewById(R.id.constraintLayoutmain);
    }

    // set on click listener
    private void setOnClick() {
        save.setOnClickListener(v -> {
            //todo: save the data

        });
        back.setOnClickListener(v -> {
            //todo: go back
        });
        image.setOnClickListener(v -> {
            //todo: change the image
        });

        constraintLayoutmain.setOnClickListener(v -> {
            // to hide the keyboard when the user clicks on the login button'
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    // to hide the keyboard when the user clicks anywhere on the screen
    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
