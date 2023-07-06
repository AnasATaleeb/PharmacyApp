package com.example.pharmacy.Adaptor;

import com.bumptech.glide.Glide;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmacy.R;
import com.example.pharmacy.Activity.Customer.ViewItem;
import com.example.pharmacy.databinding.ItemBinding;
import com.example.pharmacy.databinding.SearchItemBinding;
import com.example.pharmacy.model.Item;
import com.google.gson.Gson;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewHolder> {

    Context context;
    ArrayList<Item> list;

    public SearchAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    public void setItemFilterList(ArrayList<Item> arrFilterList){
        this.list = arrFilterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final Item model = list.get(position);
        holder.binding.loveName.setText(model.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(model.getPic())
                .into(holder.binding.lovePic);
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
    }


    @Override
    public int getItemCount() {
        return  list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        SearchItemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SearchItemBinding.bind(itemView);
        }
    }
}
