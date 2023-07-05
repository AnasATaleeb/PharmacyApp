package com.example.pharmacy.Adaptor;

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

import com.bumptech.glide.Glide;
import com.example.pharmacy.Activity.Customer.ViewItem;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.ShowItemConformationBinding;
import com.example.pharmacy.model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ConformationItemAdapter extends RecyclerView.Adapter<ConformationItemAdapter.viewHolder> {

    Context context;
    ArrayList<Item> list;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ConformationItemAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.show_item_conformation, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Item model = list.get(position);

        holder.binding.orderName.setText(model.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(model.getPic())
                .into(holder.binding.orderImg);
        holder.binding.tvPrice.setText(model.getPrice() + " شيكل");
        holder.binding.tvQuantity2.setText(model.getNumberOfItem() + "");
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

        holder.binding.removeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                CollectionReference favoriteRef = db.collection("Users").document(mAuth.getUid()).collection("Cart");

                favoriteRef.orderBy("name").limit(1).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().delete();
                        }
                        Toast.makeText(v.getContext(), "تم حذف العنصر من السلة", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    } else {
                        Log.d("RemoveFavorite", "Error getting documents: ", task.getException());
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ShowItemConformationBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ShowItemConformationBinding.bind(itemView);
        }
    }
}
