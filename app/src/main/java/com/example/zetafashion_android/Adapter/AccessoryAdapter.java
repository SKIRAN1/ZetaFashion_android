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
import com.example.zetafashion_android.Model.AccessoryProducts;
import com.example.zetafashion_android.Model.ShoeProducts;
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

public class AccessoryAdapter extends RecyclerView.Adapter<AccessoryAdapter.MyViewHolder>{

    Context context;
    ArrayList<AccessoryProducts> accessoryProducts;

    DatabaseReference databaseReference;
    FirebaseUser user;
    String Phone;

    public AccessoryAdapter(Context context, ArrayList<AccessoryProducts> accessoryProducts) {
        this.context = context;
        this.accessoryProducts = accessoryProducts;
    }
    @NonNull
    @Override
    public AccessoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoryAdapter.MyViewHolder holder, int position) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();

        AccessoryProducts accessoryProduct = accessoryProducts.get(position);
        holder.productName.setText(accessoryProduct.getProductName());
        holder.productPrice.setText(accessoryProduct.getProductPrice());
        Glide.with(context).load(accessoryProduct.getProductImageUrl()).into(holder.productImage);

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
                        userdataMap.put("ProductName", accessoryProduct.getProductName());
                        userdataMap.put("ProductPrice", accessoryProduct.getProductPrice());
                        userdataMap.put("ProductCategory", accessoryProduct.getProductCategory());
                        userdataMap.put("ProductImageUrl", accessoryProduct.getProductImageUrl());
                        userdataMap.put("ProductQuantity", "1");

                        databaseReference.child(Phone).child("Cart Items").child(accessoryProduct.getProductName()).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        return accessoryProducts.size();
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
