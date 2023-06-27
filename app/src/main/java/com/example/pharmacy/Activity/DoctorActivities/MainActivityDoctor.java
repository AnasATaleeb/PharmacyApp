package com.example.pharmacy.Activity.DoctorActivities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.AllItems;
import com.example.pharmacy.Activity.Categories;
import com.example.pharmacy.Activity.Profile;
import com.example.pharmacy.Adaptor.CategoryAdapter;
import com.example.pharmacy.Adaptor.ItemsAdapterDoctor;
import com.example.pharmacy.Decorator.GridSpacingItemDecoration;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityMainDoctorBinding;
import com.example.pharmacy.model.Category;
import com.example.pharmacy.model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivityDoctor extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MeowBottomNavigation bottomNavigation;
    private @NonNull ActivityMainDoctorBinding binding;
    private ArrayList<Category> categories;
    private ArrayList<Item> items;
    private CardView add_Item;
    Intent intent;

    TextView allCategory, profileLoc;
    TextView allitms;

    ShapeableImageView pofile;

    private TextView profileHello;
    private FirebaseAuth mAuth;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // hide action bar
        getSupportActionBar().hide();

        // initialize views
        initialize();

        threadCategoriesSetUp.start();
        threadGetItemsFromFireStore.start();

        pofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivityDoctor.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivityDoctor.this, Categories.class);
                startActivity(intent);
            }
        });

        allitms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivityDoctor.this, AllItems.class);
                startActivity(intent);
            }
        });

        add_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivityDoctor.this, AddItem.class);
                startActivity(intent);
            }
        });

       /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });*/

        loadProfileInformation();
        bottomNavigationSetUp();

    }

/*    private void filterList(String text) {
        List<Item> filteredList = new ArrayList<>();
        for (Item item : items) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, "العنصر غير موجود", Toast.LENGTH_LONG).show();
        }else {

        }
    }
    */

    private void initialize() {
        pofile = findViewById(R.id.userImg);
        profileHello = findViewById(R.id.profileHello);
        mAuth = FirebaseAuth.getInstance();
        allCategory = findViewById(R.id.all_cat);
        allitms = findViewById(R.id.all_itms);
        pofile = findViewById(R.id.userImg);
        add_Item = findViewById(R.id.add_Item);
        profileLoc = findViewById(R.id.userLoc);
        /*searchView = findViewById(R.id.search);
        searchView.clearFocus();*/
    }

    private void loadProfileInformation() {
        DocumentReference docRef = db.collection("Users").document(mAuth.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    profileLoc.setText(documentSnapshot.getString("location"));
                } else
                    Toast.makeText(getApplicationContext(), "خطأ في جلب المعلومات", Toast.LENGTH_SHORT);
            }
        });
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(this).load(user.getPhotoUrl()).into(pofile);
            }
            if (user.getDisplayName() != null) {
                profileHello.setText("مرحبا " + user.getDisplayName());
            }

        }
    }

    private void bottomNavigationSetUp() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.outline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.tracking));
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(MainActivityDoctor.this, SettingDoctors.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(MainActivityDoctor.this, MainActivityDoctor.class);
                        startActivity(intent);
                        finish();
                        break;

                    case 3:
                        intent = new Intent(MainActivityDoctor.this, OrderStatus.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return null;
            }
        });

        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                return null;
            }
        });
    }

    private void categoriesSetUp() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.categoryList.setLayoutManager(linearLayoutManager);

        categories = new ArrayList<>();
        categories.add(new Category("كل المنتجات", R.drawable.all));
        categories.add(new Category("ادوية", R.drawable.drug));
        categories.add(new Category("ادوات طبية", R.drawable.syringe));
        categories.add(new Category("العناية بالوجه", R.drawable.skin));
        categories.add(new Category("مستلزمات الاطفال", R.drawable.milkpowder));
        categories.add(new Category("ميك اب", R.drawable.makeup));
        categories.add(new Category("العناية الشخصية", R.drawable.personalcare4));

        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        binding.categoryList.setAdapter(adapter);
    }

    private void ItemsSetUp() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsList.setLayoutManager(linearLayoutManager);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.itemsList);
        RecyclerView.LayoutManager layoutManager;
        RecyclerView.Adapter adapter;

        // Set the span count to 2 and create a GridLayoutManager
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Apply the item decoration with the desired spacing
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));


        // Set your adapter and data to the RecyclerView
        adapter = new ItemsAdapterDoctor(this, items); // Replace 'YourAdapter' and 'data' with your actual adapter and data
        recyclerView.setAdapter(adapter);
    }

    private void getItemsFromFireStore() {
        items = new ArrayList<>();
        db.collection("Items").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String name = document.getString("name");
                    String description = document.getString("description");
                    String pic = document.getString("image");
                    String price = document.getString("price");
                    int quantity = Integer.parseInt(document.getString("size"));
                    String cat = document.getString("category");

                    Item item = new Item(name, description, pic, price, quantity, cat);
                    items.add(item);
                    Log.d(TAG, document.getId() + " => " + document.getData());
                }

                ItemsSetUp();
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

    }

    Thread threadCategoriesSetUp = new Thread() {
        @Override
        public void run() {
            super.run();
            categoriesSetUp();
            Log.d(TAG, "categoriesSetUp");
        }
    };

    Thread threadGetItemsFromFireStore = new Thread() {
        @Override
        public void run() {
            super.run();
            getItemsFromFireStore();
            Log.d(TAG, "getItemsFromFireStore");
        }
    };
}
