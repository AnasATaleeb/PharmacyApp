package com.example.pharmacy.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.example.pharmacy.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {
    private Context ct;
    private ArrayList<Order> arr = new ArrayList<>();

    public OrderAdapter(@NonNull Context context, int resource, @NonNull List<Order> list){
        super(context,resource,list);
        this.ct = context;
        this.arr= new ArrayList<>(list);
    }

    public OrderAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView (int position, @NonNull View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.orders_item,null);
        }

        return convertView;
    }

}
