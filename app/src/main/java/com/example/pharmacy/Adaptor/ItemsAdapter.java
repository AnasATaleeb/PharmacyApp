package com.example.pharmacy.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.R;
import com.example.pharmacy.Activity.ViewItem;
import com.example.pharmacy.databinding.ItemBinding;
import com.example.pharmacy.model.Item;

import java.util.ArrayList;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.viewHolder> {

    Context context;
    ArrayList<Item> list;

    public ItemsAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final Item model = list.get(position);

        holder.binding.itemTitle.setText(model.getTitle());
        Drawable drawable = ContextCompat.getDrawable(context, model.getPic());
        holder.binding.itemPic.setImageDrawable(drawable);
        holder.binding.desItem.setText(model.getDiscreption());
        holder.binding.costItem.setText(model.getPrice()+" شيكل");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewItem.class);
                context.startActivity(intent);
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
