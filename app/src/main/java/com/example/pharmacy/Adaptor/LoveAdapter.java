package com.example.pharmacy.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;

import java.util.ArrayList;
import java.util.List;

public class LoveAdapter extends ArrayAdapter<Item> {
    private Context ct;
    private TextView love_name;
    private ImageView love_pic;
    private ArrayList<Item> arr = new ArrayList<>();

    public LoveAdapter(@NonNull Context context, int resource, @NonNull List<Item> list){
        super(context,resource,list);
        this.ct = context;
        this.arr= new ArrayList<>(list);
    }

    @NonNull
    @Override
    public View getView (int position, @NonNull View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.love_item,null);
        }
        if (arr.size()>0){
            Item item = arr.get(position);

            love_name = convertView.findViewById(R.id.love_name);
            love_pic = convertView.findViewById(R.id.love_pic);

            Glide.with(convertView.getContext())
                    .load(item.getPic())
                    .into(love_pic);

            love_name.setText(item.getTitle());

        }
        return convertView;
    }

}
