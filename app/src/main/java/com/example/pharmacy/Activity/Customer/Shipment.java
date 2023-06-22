package com.example.pharmacy.Activity.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.R;

public class Shipment extends AppCompatActivity {

    private Spinner spinner;
    private EditText address;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_shipment);
        getSupportActionBar().hide();

        // 1. Initialize the UI components
        init();

        //2. Set the onClickListener for the button
        setOnClick();

        // todo set the spinner adapter
    }

    private void init() {
        spinner = findViewById(R.id.spinner_city);
        address = findViewById(R.id.edit_postal);
        btn = findViewById(R.id.btn_next);
    }

    private void setOnClick() {
        btn.setOnClickListener(v -> {
            if(address.getText().toString().isEmpty()) {
                address.setError("Please enter your address");
                return;
            }
//            if (spinner.getSelectedItem().toString().equals("Select City")) {
                //return;
     //       }
            Intent intent = new Intent(Shipment.this, Payment.class);
            startActivity(intent);

        });
    }
}
