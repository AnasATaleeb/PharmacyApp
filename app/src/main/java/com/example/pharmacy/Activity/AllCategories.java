package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pharmacy.Adaptor.AllCategoriesAdapter;
import com.example.pharmacy.Adaptor.ItemsAdapter;
import com.example.pharmacy.Decorator.GridSpacingItemDecoration;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityAllCategoriesBinding;
import com.example.pharmacy.databinding.ActivityAllItemsBinding;
import com.example.pharmacy.model.Category;

import java.util.ArrayList;

public class AllCategories extends AppCompatActivity {

    ActivityAllCategoriesBinding binding;
    ArrayList<Category> categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        categories = new ArrayList<>();
        categories.add(new Category("كل المنتجات", R.drawable.all));
        categories.add(new Category("ادوية", R.drawable.drug));
        categories.add(new Category("ادوات طبية", R.drawable.syringe));
        categories.add(new Category("العناية بالوجه", R.drawable.skin));
        categories.add(new Category("مستلزمات الاطفال", R.drawable.milkpowder));
        categories.add(new Category("ميك اب", R.drawable.makeup));
        categories.add(new Category("العناية الشخصية", R.drawable.personalcare4));
        ItemsSetUp();

    }

    private void ItemsSetUp() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.itemsList.setLayoutManager(linearLayoutManager);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.itemsList);
        RecyclerView.LayoutManager layoutManager ;
        RecyclerView.Adapter adapter;

        // Set the span count to 2 and create a GridLayoutManager
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Apply the item decoration with the desired spacing
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));

        // Set your adapter and data to the RecyclerView
        adapter = new AllCategoriesAdapter(this,categories); // Replace 'YourAdapter' and 'data' with your actual adapter and data
        recyclerView.setAdapter(adapter);
    }


}