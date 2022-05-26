package com.example.zetafashion_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zetafashion_android.Model.WomenProducts;
import com.example.zetafashion_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class WomenAdapter extends RecyclerView.Adapter<WomenAdapter.MyViewHolder> {
    Context context;
    ArrayList<WomenProducts> womenProducts;

    DatabaseReference databaseReference;
    FirebaseUser user;
    String Phone;

    public WomenAdapter(Context context, ArrayList<WomenProducts> womenProducts) {
        this.context = context;
        this.womenProducts = womenProducts;
    }

    @NonNull
    @Override
    public WomenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WomenAdapter.MyViewHolder holder, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();

        WomenProducts womenProduct = womenProducts.get(position);
        holder.productName.setText(womenProduct.getProductName());
        holder.productPrice.setText(womenProduct.getProductPrice());
        Glide.with(context).load(womenProduct.getProductImageUrl()).into(holder.productImage);
        holder.btn_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.orderByChild("Email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Phone = dataSnapshot.child("Phone").getValue().toString();
                        }
                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("ProductName", womenProduct.getProductName());
                        userdataMap.put("ProductPrice", womenProduct.getProductPrice());
                        userdataMap.put("ProductCategory", womenProduct.getProductCategory());
                        userdataMap.put("ProductImageUrl", womenProduct.getProductImageUrl());
                        userdataMap.put("ProductQuantity", "1");

                        databaseReference.child(Phone).child("Cart Items").child(womenProduct.getProductName()).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Error, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return womenProducts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice;
        ImageView productImage;
        Button btn_addCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.pro_Name);
            productPrice = itemView.findViewById(R.id.pro_price);
            productImage = itemView.findViewById(R.id.pro_image);
            btn_addCart = itemView.findViewById(R.id.btn_addCart);
        }
    }
}
