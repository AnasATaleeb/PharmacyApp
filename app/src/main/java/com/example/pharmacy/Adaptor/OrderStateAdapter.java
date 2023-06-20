package com.example.pharmacy.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.pharmacy.R;
import com.example.pharmacy.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderStateAdapter extends ArrayAdapter<Order> {
    private Context ct;
    private ArrayList<Order> arr = new ArrayList<>();

    public OrderStateAdapter(@NonNull Context context, int resource, @NonNull List<Order> list){
        super(context,resource,list);
        this.ct = context;
        this.arr= new ArrayList<>(list);
    }

    public OrderStateAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView (int position, @NonNull View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.love_item,null);
        }
        if (arr.size()>0){
            Order item = arr.get(position);

        }
        return convertView;
    }

}
