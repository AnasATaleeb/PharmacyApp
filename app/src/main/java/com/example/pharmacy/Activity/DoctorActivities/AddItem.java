package com.example.pharmacy.Activity.DoctorActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmacy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    Uri uriImg;
    String profileUrl = "";
    private TextInputEditText itemName, itemDes, itemPrice, itemSize, itemCat;
    private ImageView itemImg, add_img;
    Button btnAdd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Hide the action bar
        getSupportActionBar().hide();

        // initialize views
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
                if (isInputComplete()) {
                    uploadImg();
                }
            }
        });
    }

    private void insertItem() {
        // insert item to database
        Map<String, String> dataToSave = new HashMap<>();
        dataToSave.put("name", itemName.getText().toString());
        dataToSave.put("category", itemCat.getText().toString());
        dataToSave.put("description", itemDes.getText().toString());
        dataToSave.put("price", itemPrice.getText().toString());
        dataToSave.put("size", itemSize.getText().toString());
        dataToSave.put("image", profileUrl);

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
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImg = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImg);
                itemImg.setImageBitmap(bitmap);
            } catch (Exception e) {
            }
        }
    }

    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "اختر صورة الحساب 🥰"), CHOOSE_IMAGE);
    }

    private void uploadImg() {
        StorageReference imgRef = FirebaseStorage.getInstance().getReference("itemPic/" + System.currentTimeMillis() + ".jpg");
        imgRef.putFile(uriImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(getApplicationContext(), "تم رفع الصورة", Toast.LENGTH_SHORT).show();
                //profileUrl= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getApplicationContext(), "تم جلب url", Toast.LENGTH_SHORT).show();
                        profileUrl = uri.toString();
                        insertItem();
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

    private boolean isInputComplete() {

        // check if name is not null
        if (itemName.getText().toString().isEmpty()) {
            itemName.setError("الرجاء ادخال اسم المنتج");
            itemName.requestFocus();
            return false;
        }

        // check if category is not null
        if (itemCat.getText().toString().isEmpty()) {
            itemCat.setError("الرجاء ادخال نوع المنتج");
            itemCat.requestFocus();
            return false;
        }

        // check if description is not null
        if (itemDes.getText().toString().isEmpty()) {
            itemDes.setError("الرجاء ادخال وصف المنتج");
            itemDes.requestFocus();
            return false;
        }

        // check if price is not null
        if (itemPrice.getText().toString().isEmpty()) {
            itemPrice.setError("الرجاء ادخال سعر المنتج");
            itemPrice.requestFocus();
            return false;
        }

        // check if size is not null
        if (itemSize.getText().toString().isEmpty()) {
            itemSize.setError("الرجاء ادخال حجم المنتج");
            itemSize.requestFocus();
            return false;
        }
        return true;
    }


}