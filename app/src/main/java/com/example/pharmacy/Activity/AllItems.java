package com.example.pharmacy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pharmacy.Adaptor.ItemsAdapter;
import com.example.pharmacy.Decorator.GridSpacingItemDecoration;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ActivityAllItemsBinding;
import com.example.pharmacy.databinding.ActivityMainBinding;
import com.example.pharmacy.model.Item;

import java.util.ArrayList;

public class AllItems extends AppCompatActivity {
    ActivityAllItemsBinding binding;
    private ArrayList<Item> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
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

        items = new ArrayList<>();
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));
        items.add(new Item("بانادول" , "لعلاج البرد والرشخ والزكام - 20 قرص","https://firebasestorage.googleapis.com/v0/b/pharmacy-589b4.appspot.com/o/profilepic%2F1687784328564.jpg?alt=media&token=2659115f-3628-4f94-89a7-5a942fe7123b" ,"24.5",7));


        // Set your adapter and data to the RecyclerView
        adapter = new ItemsAdapter(this,items); // Replace 'YourAdapter' and 'data' with your actual adapter and data
        recyclerView.setAdapter(adapter);
    }
}