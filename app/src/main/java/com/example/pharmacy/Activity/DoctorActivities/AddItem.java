package com.example.pharmacy.Activity.DoctorActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pharmacy.Activity.MainSign;
import com.example.pharmacy.Activity.Register;
import com.example.pharmacy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddItem extends AppCompatActivity {

    private static final int CHOOSE_IMAGE =101;
    Uri uriImg;
    String profileUrl;
    private TextInputEditText itemName,itemDes,itemPrice,itemSize,itemCat;
    private ImageView itemImg,add_img;
    Button btnAdd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        getSupportActionBar().hide();

        initialization();

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem();
            }
        });
    }

    private void insertItem() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Map<String, String> dataToSave = new HashMap<>();
        dataToSave.put("name",itemName.getText().toString());
        dataToSave.put("category", itemCat.getText().toString());
        dataToSave.put("description",itemDes.getText().toString());
        dataToSave.put("price",itemPrice.getText().toString());
        dataToSave.put("size",itemSize.getText().toString());
        dataToSave.put("image",profileUrl);

        db.collection("Items").
                add(dataToSave).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast toast = Toast.makeText(AddItem.this, "تم اضافة المنتج بنجاح 🥳", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(AddItem.this, MainActivityDoctor.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast = Toast.makeText(AddItem.this, "خطأ في اضافة المنتج 😥", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriImg = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImg);
                itemImg.setImageBitmap(bitmap);
            }catch (Exception e){
            }
        }
    }

    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "اختر صورة الحساب 🥰"), CHOOSE_IMAGE);
    }

    private void uploadImg(){
        StorageReference imgRef = FirebaseStorage.getInstance().getReference("profilepic/"+ System.currentTimeMillis() +".jpg");
        imgRef.putFile(uriImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "تم رقع الصورة", Toast.LENGTH_SHORT).show();
                //profileUrl= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getApplicationContext(),"تم جلب url",Toast.LENGTH_SHORT).show();
                        profileUrl = uri.toString();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void initialization() {
        itemDes = findViewById(R.id.desc);
        itemImg = findViewById(R.id.addImg);
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.price);
        itemSize = findViewById(R.id.size);
        btnAdd = findViewById(R.id.btnAdd);
        add_img = findViewById(R.id.add_img);
        itemCat = findViewById(R.id.itemCat);
    }
}