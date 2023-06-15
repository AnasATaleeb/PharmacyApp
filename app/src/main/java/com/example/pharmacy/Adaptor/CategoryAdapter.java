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

import com.example.pharmacy.Activity.Categories;
import com.example.pharmacy.Activity.MainSign;
import com.example.pharmacy.R;
import com.example.pharmacy.databinding.CategoryBinding;
import com.example.pharmacy.model.Category;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

    Context context;
    ArrayList<Category> list;

    public CategoryAdapter(Context context, ArrayList<Category> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final Category model = list.get(position);

        holder.binding.itemTitle.setText(model.getTitle());
        Drawable drawable = ContextCompat.getDrawable(context, model.getPic());
        holder.binding.itemPic.setImageDrawable(drawable);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Categories.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return  list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        CategoryBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CategoryBinding.bind(itemView);
        }
    }
}
