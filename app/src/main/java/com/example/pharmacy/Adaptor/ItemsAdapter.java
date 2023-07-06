package com.example.pharmacy.Adaptor;

import com.bumptech.glide.Glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.R;
import com.example.pharmacy.Activity.Customer.ViewItem;
import com.example.pharmacy.databinding.ItemBinding;
import com.example.pharmacy.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.viewHolder> {

    Context context;
    ArrayList<Item> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    public ItemsAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
        mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Item model = list.get(position);
        holder.binding.itemTitle.setText(model.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(model.getPic())
                .into(holder.binding.itemPic);
        holder.binding.desItem.setText(model.getDiscreption());
        holder.binding.costItem.setText(model.getPrice()+" شيكل");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewItem.class);

                Item model = list.get(holder.getAdapterPosition());
                if (model != null) {
                    String itemJson = new Gson().toJson(model);
                    intent.putExtra("item_to_view", itemJson);
                }

                context.startActivity(intent);
            }
        });

        holder.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = list.get(position);
                Map<String, String> dataToSave = new HashMap<>();
                dataToSave.put("name", item.getTitle());
                dataToSave.put("category", item.getCategory());
                dataToSave.put("description", item.getDiscreption());
                dataToSave.put("price", item.getPrice());
                dataToSave.put("size", item.getQuantity() + "");
                dataToSave.put("image", item.getPic());
                dataToSave.put("numberOfItem", "1");

                db.collection("Users").document(mAuth.getUid()).collection("Cart")
                        .add(dataToSave).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast toast = Toast.makeText(context, "تم اضافة المنتج الى العربة", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("خطأ :", e.toString());
                                Toast toast = Toast.makeText(context, "خطأ في اضافة المنتج الى العربة", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return  list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ItemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBinding.bind(itemView);
        }
    }
}
