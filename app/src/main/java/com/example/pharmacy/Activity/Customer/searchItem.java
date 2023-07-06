package com.example.pharmacy.Activity.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.pharmacy.Adaptor.ItemsAdapter;
import com.example.pharmacy.Adaptor.SearchAdapter;
import com.example.pharmacy.Decorator.GridSpacingItemDecoration;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class searchItem extends AppCompatActivity {

    private SearchView search;
    private RecyclerView recyclerView;

    private ArrayList<Item> items = new ArrayList<>();

    private SearchAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        search = findViewById(R.id.search);
        search.clearFocus();
        threadGetItemsFromFireStore.start();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return false;
            }
        });


        // Hide the action bar
        getSupportActionBar().hide();
    }

    private void fileList(String text) {
        ArrayList<Item> filterItemList = new ArrayList<>();
        for (Item item: items){
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())){
                filterItemList.add(item);
            }
        }

        if (filterItemList.isEmpty()){
            Toast.makeText(this,"لا يوجد منتج مشابه", Toast.LENGTH_SHORT).show();
        }else {
            adapter.setItemFilterList(filterItemList);
        }
    }

    private void ItemsSetUp() {
        recyclerView = findViewById(R.id.categoryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(this,items);
        recyclerView.setAdapter(adapter);
    }

    private void getItemsFromFireStore() {
        db.collection("Items").get()
                .addOnCompleteListener(task -> {
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
                        }

                        ItemsSetUp();
                    } else {
                    }
                });
    }

    Thread threadGetItemsFromFireStore = new Thread(new Runnable() {
        @Override
        public void run() {
            getItemsFromFireStore();
        }
    });
}