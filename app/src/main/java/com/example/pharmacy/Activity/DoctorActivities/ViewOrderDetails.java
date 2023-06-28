package com.example.pharmacy.Activity.DoctorActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.pharmacy.Adaptor.ConformationItemAdapter;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityPayConfirmationBinding;
import com.example.pharmacy.databinding.ActivityViewOrderDetailsBinding;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ViewOrderDetails extends AppCompatActivity {
    ActivityViewOrderDetailsBinding binding;
    TextView textView48, final_price,textViewShip;
    private FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        textView48 = findViewById(R.id.textView48);
        final_price = findViewById(R.id.final_price);
        textViewShip = findViewById(R.id.textViewShip);
        mAuth = FirebaseAuth.getInstance();


        String s = getIntent().getStringExtra("order");
        Gson gson = new Gson();
        Order o = gson.fromJson(s, Order.class);

        textViewShip.setText(o.getLocation());
        final_price.setText(" إجمالي الطلب: " + o.getTotalPrice() + " شيكل");
        textView48.setText("تم ارسال الطلب الى الموقع");

  /*      String collectionPath = getIntent().getStringExtra("order_path");
        Log.v("----====================>>>>>>>", collectionPath);

// Create a document reference using the correct path
        DocumentReference documentRef = FirebaseFirestore.getInstance().document(collectionPath);

// Rest of your code...
        // Create a map to specify the field and its updated value
        Map<String, Object> updates = new HashMap<>();
        updates.put("status", "تم ارسال الطلب الى الموقع");

        // Update the field in the document
/*        documentRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Field update successful
                    }
                });
*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsOder.setLayoutManager(linearLayoutManager);

        ConformationItemAdapter adapter = new ConformationItemAdapter(this, o.getItems());
        binding.itemsOder.setAdapter(adapter);


    }

}