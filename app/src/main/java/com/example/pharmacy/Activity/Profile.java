package com.example.pharmacy.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pharmacy.Activity.Customer.MainActivity;
import com.example.pharmacy.Activity.DeliveryActivities.MainDelivery;
import com.example.pharmacy.Activity.DoctorActivities.MainActivityDoctor;
import com.example.pharmacy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {

    CardView cardView;
    ShapeableImageView pofile;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView profName,profEmail,profPhone,profLocation;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        cardView = findViewById(R.id.add_Item);
        pofile = findViewById(R.id.userImg);
        mAuth = FirebaseAuth.getInstance();
        profName = findViewById(R.id.profName);
        profEmail = findViewById(R.id.profEmail);
        profPhone = findViewById(R.id.profPhone);
        profLocation = findViewById(R.id.profLocation);

        loadProfileInformation();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("Users").document(mAuth.getCurrentUser().getUid());
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            if(documentSnapshot.getString("role").equals("doctor")){
                                Intent intent = new Intent(Profile.this, MainActivityDoctor.class);
                                startActivity(intent);
                            } else if (documentSnapshot.getString("role").equals("patient")) {
                                Intent intent = new Intent(Profile.this,MainActivity.class);
                                startActivity(intent);
                            }else if (documentSnapshot.getString("role").equals("delivery")) {
                                Intent intent = new Intent(Profile.this, MainDelivery.class);
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
        });
    }

    private void loadProfileInformation(){
        DocumentReference docRef = db.collection("Users").document(mAuth.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    profPhone.setText(documentSnapshot.getString("phone"));
                    profLocation.setText(documentSnapshot.getString("location"));
                }else
                    Toast.makeText(getApplicationContext(),"خطأ في جلب المعلومات" , Toast.LENGTH_SHORT);
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null){
            if(user.getPhotoUrl() != null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(pofile);
            }
            if (user.getDisplayName() != null){
                profName.setText( user.getDisplayName());
            }

            profEmail.setText(user.getEmail());

            profPhone.setText(user.getPhoneNumber());

        }
    }
}