package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pharmacy.Activity.AllItems;
import com.example.pharmacy.Activity.Categories;
import com.example.pharmacy.Activity.Profile;
import com.example.pharmacy.Adaptor.CategoryAdapter;
import com.example.pharmacy.Adaptor.ItemsAdapter;
import com.example.pharmacy.Decorator.GridSpacingItemDecoration;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityMainBinding;
import com.example.pharmacy.model.Category;
import com.example.pharmacy.model.Item;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    private ActivityMainBinding binding;
    private ArrayList<Category> categories;
    private ArrayList<Item> items;

    private TextView profileHello;
    Intent intent;

    TextView allCategory;
    TextView allitms;

    ShapeableImageView pofile;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        allCategory = findViewById(R.id.all_cat);
        allitms = findViewById(R.id.all_itms);
        pofile = findViewById(R.id.userImg);
        profileHello = findViewById(R.id.profileHello);
        mAuth = FirebaseAuth.getInstance();
        loadProfileInformation();
        pofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        allCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, Categories.class);
                startActivity(intent);
            }
        });

        allitms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AllItems.class);
                startActivity(intent);
            }
        });

        bottomNavigationSetUp();
        categoriesSetUp();
        ItemsSetUp();
    }

    private void loadProfileInformation(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null){
            if(user.getPhotoUrl() != null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(pofile);
            }
            if (user.getDisplayName() != null){
                profileHello.setText( "مرحبا "+user.getDisplayName());
            }

        }
    }

    private void bottomNavigationSetUp() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.outline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.cart));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.love));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.logistics));
        bottomNavigation.show(3, true);

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES

                switch (model.getId()) {
                    case 1:
                        intent = new Intent(MainActivity.this, Cart.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent = new Intent(MainActivity.this, Love.class);
                        startActivity(intent);
                        break;

                    case 3:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;

                    case 4:
                        intent = new Intent(MainActivity.this, OrderActivity.class);
                        startActivity(intent);
                        break;

                    case 5:
                        Intent intent = new Intent(MainActivity.this, Setting.class);
                        startActivity(intent);
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
        categories.add(new Category("أدوية برد", R.drawable.drug));
        categories.add(new Category("ابر وسرنجات", R.drawable.syringe));
        categories.add(new Category("عناية بالبشرة", R.drawable.skin));
        categories.add(new Category("حليب أطفال", R.drawable.milkpowder));

        CategoryAdapter adapter = new CategoryAdapter(this, categories);
        binding.categoryList.setAdapter(adapter);
    }

    private void ItemsSetUp() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsList.setLayoutManager(linearLayoutManager);

        RecyclerView recyclerView = (RecyclerView) findViewById(
                R.id.itemsList);
        RecyclerView.LayoutManager layoutManager ;
        RecyclerView.Adapter adapter;

        // Set the span count to 2 and create a GridLayoutManager
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Apply the item decoration with the desired spacing
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));

        items = new ArrayList<>();
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,25.8));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,25.8));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,25.8));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,25.8));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص",R.drawable.panadolextra ,25.8));


        // Set your adapter and data to the RecyclerView
        adapter = new ItemsAdapter(this,items); // Replace 'YourAdapter' and 'data' with your actual adapter and data
        recyclerView.setAdapter(adapter);
    }
}
