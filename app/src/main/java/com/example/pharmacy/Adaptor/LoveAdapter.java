package com.example.pharmacy.Adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.pharmacy.Activity.Customer.Cart;
import com.example.pharmacy.R;
import com.example.pharmacy.model.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoveAdapter extends ArrayAdapter<Item> {
    private Context ct;
    private TextView love_name;
    private ImageView love_pic,remove;
    private ArrayList<Item> arr = new ArrayList<>();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


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
            remove = convertView.findViewById(R.id.remove);

            Glide.with(convertView.getContext())
                    .load(item.getPic())
                    .into(love_pic);

            love_name.setText(item.getTitle());

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arr.remove(position);
                    CollectionReference favoriteRef = db.collection("Users").document(mAuth.getUid()).collection("Favorite");

                    favoriteRef.orderBy("name").limit(1).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete();
                            }
                            Toast.makeText(LoveAdapter.this.getContext(), "تم حذف العنصر من المفضلة", Toast.LENGTH_LONG).show();
                            //TODO: rebuild adapter!!
                        } else {
                            Log.d("RemoveFavorite", "Error getting documents: ", task.getException());
                        }
                    });
                }
            });

        }
        return convertView;
    }

}
