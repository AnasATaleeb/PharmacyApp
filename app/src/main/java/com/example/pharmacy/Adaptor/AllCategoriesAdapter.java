package com.example.pharmacy.Adaptor;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.Activity.Categories;
import com.example.pharmacy.Activity.MainSign;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.AllCatItemBinding;
import com.example.pharmacy.databinding.CategoryBinding;
import com.example.pharmacy.model.Category;
import com.example.pharmacy.model.Item;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;



public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.viewHolder> {

    Context context;
    ArrayList<Category> list;
    ArrayList<Item>items = new ArrayList<>();

    int pos;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public AllCategoriesAdapter(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.all_cat_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Category model = list.get(position);

        holder.binding.itemTitle.setText(model.getTitle());
        Drawable drawable = ContextCompat.getDrawable(context, model.getPic());
        holder.binding.itemPic.setImageDrawable(drawable);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                threadGetItemsFromFireStore.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return  list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        AllCatItemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AllCatItemBinding.bind(itemView);
        }
    }

    Thread threadGetItemsFromFireStore = new Thread(new Runnable() {
        @Override
        public void run() {
            if (list.get(pos).getTitle().equals("كل المنتجات") ) {
                db.collection("Items")
                        .get()
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
                                    Intent intent = new Intent(context, Categories.class);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(items);
                                    intent.putExtra("itemsJson", json);
                                    context.startActivity(intent);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        });
            }else {

                db.collection("Items")
                        .whereEqualTo("category", (list.get(pos).getTitle()))
                        .get()
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
                                    Intent intent = new Intent(context, Categories.class);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(items);
                                    intent.putExtra("itemsJson", json);
                                    context.startActivity(intent);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        });
            }
        }
    });

}
