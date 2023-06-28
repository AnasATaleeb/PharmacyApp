package com.example.pharmacy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pharmacy.R;
import com.example.pharmacy.model.Delivery;
import com.example.pharmacy.model.Doctor;
import com.example.pharmacy.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final int CHOOSE_IMAGE =101;
    Uri uriImg;
    String profileUrl;
    private ShapeableImageView userImg;
    private TextInputEditText userName;
    private TextInputEditText userEmail;
    private TextInputEditText userPhone;
    private TextInputEditText userLocation;
    private TextInputEditText userPassword;

    private ImageView regesterImg;

    private RadioGroup group;

    private Button register;

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;
    private ConstraintLayout con;

    DocumentReference mDocRef = FirebaseFirestore.getInstance().document("pharmacy/patient");

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // hide action bar
        getSupportActionBar().hide();

        //connect to firebase
        mAuth = FirebaseAuth.getInstance();
        initializeData();
        
        register.setAlpha(0f);
        register.setTranslationY(50);
        register.animate().alpha(1f).translationYBy(-60).setDuration(1500);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        regesterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        con.setOnClickListener(v ->{
            // to hide the keyboard when the user clicks on the login button'
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        showConfirmationDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriImg = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImg);
                userImg.setImageBitmap(bitmap);
            }catch (Exception e){
            }
        }
    }

    private void uploadImg(){
        StorageReference imgRef = FirebaseStorage.getInstance().getReference("profilepic/"+ System.currentTimeMillis() +".jpg");
        imgRef.putFile(uriImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //profileUrl= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        profileUrl = uri.toString();
                        updateUserImage();
                    }
                });
            }
        });
    }

    private void updateUserImage(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null && profileUrl!= null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userName.getText().toString())
                    .setPhotoUri(Uri.parse(profileUrl))
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"تم تحديث الحساب والصورة",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void getImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "اختر صورة الحساب 🥰"), CHOOSE_IMAGE);
    }

    private void registerUser(){
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
            addDoctor();
        }
        if (value.equals("عميل")){
            addPatient();
        }
        if (value.equals("موظف توصيل")) {
            addDelivery();
        }
    }

    //TODO: remove doctor radio
    private void addDoctor() {
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

                        uploadImg();
                        Doctor doctor = new Doctor(userName.getText().toString(), userPhone.getText().toString(),
                                userEmail.getText().toString(),userLocation.getText().toString(),profileUrl);

                        Map<String, String> dataToSave = new HashMap<>();
                        dataToSave.put("name",doctor.getName());
                        dataToSave.put("email",doctor.getEmail());
                        dataToSave.put("phone",doctor.getPhoneNumber());
                        dataToSave.put("location",doctor.getLocation());
                        dataToSave.put("role","doctor");

                        db.collection("Users").document(mAuth.getUid()).
                                set(dataToSave, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast toast = Toast.makeText(Register.this, "تم انشاء حسابك بنجاح 🥳", Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent(Register.this,MainSign.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("خطأ :", e.toString());
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

    private void addDelivery() {
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

                        uploadImg();
                        Delivery delivery = new Delivery(userName.getText().toString(), userPhone.getText().toString(),
                                userEmail.getText().toString(),userLocation.getText().toString(),profileUrl);

                        Map<String, String> dataToSave = new HashMap<>();
                        dataToSave.put("name",delivery.getName());
                        dataToSave.put("email",delivery.getEmail());
                        dataToSave.put("phone",delivery.getPhoneNumber());
                        dataToSave.put("location",delivery.getLocation());
                        dataToSave.put("role","delivery");

                        db.collection("Users").document(mAuth.getUid())
                                .set(dataToSave, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
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

    private void addPatient() {
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

                        uploadImg();
                        Patient patient = new Patient(userName.getText().toString(), userPhone.getText().toString(),
                                userEmail.getText().toString(),userLocation.getText().toString(),profileUrl);

                        Map<String, String> dataToSave = new HashMap<>();
                        dataToSave.put("name",patient.getName());
                        dataToSave.put("email",patient.getEmail());
                        dataToSave.put("phone",patient.getPhoneNumber());
                        dataToSave.put("location",patient.getLocation());
                        dataToSave.put("role","patient");

                        db.collection("Users").document(mAuth.getUid())
                                .set(dataToSave,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                uploadImg();
                                Toast toast = Toast.makeText(Register.this, "تم انشاء حسابك بنجاح 🥳", Toast.LENGTH_SHORT);
                                toast.show();
                                Intent intent = new Intent(Register.this,MainSign.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("dsdddsds",e.toString());
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

    private void initializeData() {
        userImg = findViewById(R.id.userImg);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        con = findViewById(R.id.con);
        userLocation = findViewById(R.id.userLocation);
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        register = findViewById(R.id.btnRegister);
        group = findViewById(R.id.radioGroup);
        regesterImg = findViewById(R.id.register_img);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("تأكيد الخروج");
        builder.setMessage("هل أنت متأكد انك تريد الخروج من هذه الصفحة؟");

        // Set up the positive button
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close the page (activity)
                finish();
            }
        });

        // Set up the negative button
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog and continue with the current activity
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}