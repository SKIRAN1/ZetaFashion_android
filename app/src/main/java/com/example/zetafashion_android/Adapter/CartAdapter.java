package com.example.zetafashion_android.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.zetafashion_android.CartpageActivity;
import com.example.zetafashion_android.Model.CartProducts;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    ArrayList<CartProducts> cartProducts;
    int q;

    String Phone;
    DatabaseReference databaseReference;
    FirebaseUser user;

    public CartAdapter(Context context, ArrayList<CartProducts> cartProducts){
     this.context = context;
     this.cartProducts = cartProducts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();

        CartProducts cProduct = cartProducts.get(position);
        holder.productName.setText(cProduct.getProductName());
        holder.productPrice.setText(cProduct.getProductPrice());
        holder.quantity.setText(cProduct.getProductQuantity());
        q = Integer.parseInt(holder.quantity.getText().toString());
        Glide.with(context).load(cProduct.getProductImageUrl()).into(holder.productImage);


        holder.btn_delQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(q != 0){
                    q = q-1;
                    holder.quantity.setText(String.valueOf(q));
//                    databaseReference.orderByChild("Email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                Phone = dataSnapshot.child("Phone").getValue().toString();
//                            }
//                            HashMap<String, Object> userdataMap = new HashMap<>();
//                            userdataMap.put("ProductName", cProduct.getProductName());
//                            userdataMap.put("ProductPrice", cProduct.getProductPrice());
//                            userdataMap.put("ProductCategory", cProduct.getProductCategory());
//                            userdataMap.put("ProductImageUrl", cProduct.getProductImageUrl());
//                            userdataMap.put("ProductQuantity", String.valueOf(q));
//                            databaseReference.child(Phone).child("Cart Items").child(cProduct.getProductName()).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(context, "Error Try Again!", Toast.LENGTH_SHORT).show();
//                        }
//
//                    });
                }else{
                    databaseReference.orderByChild("Email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Phone = dataSnapshot.child("Phone").getValue().toString();
                        }
                        databaseReference.child(Phone).child("Cart Items").child(cProduct.getProductName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(context, "Removed from Cart", Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, CartpageActivity.class));
                                }
                                else {
                                    Toast.makeText(context, "Error, Try Again!", Toast.LENGTH_SHORT).show();
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

            }
        });

        holder.btn_addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (q > 0) {
                    q = q + 1;
                    holder.quantity.setText(String.valueOf(q));
//                    databaseReference.orderByChild("Email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                Phone = dataSnapshot.child("Phone").getValue().toString();
//                            }
//                            HashMap<String, Object> userdataMap = new HashMap<>();
//                            userdataMap.put("ProductName", cProduct.getProductName());
//                            userdataMap.put("ProductPrice", cProduct.getProductPrice());
//                            userdataMap.put("ProductCategory", cProduct.getProductCategory());
//                            userdataMap.put("ProductImageUrl", cProduct.getProductImageUrl());
//                            userdataMap.put("ProductQuantity", String.valueOf(q));
//                            databaseReference.child(Phone).child("Cart Items").child(cProduct.getProductName()).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
//
//                                   // holder.quantity.setText(cProduct.getProductQuantity());
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//                            Toast.makeText(context, "Error Try Again!", Toast.LENGTH_SHORT).show();
//                        }
//
//                    });
                }
                else{

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productName,productPrice,quantity;
        ImageView productImage;
        private Button btn_delQuantity,btn_addQuantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.tv_productName);
            productPrice = itemView.findViewById(R.id.tv_productPrice);
            productImage = itemView.findViewById(R.id.pro_image);
            quantity = itemView.findViewById(R.id.tv_Quantity);
            btn_addQuantity = itemView.findViewById(R.id.btn_addQuantity);
            btn_delQuantity = itemView.findViewById(R.id.btn_delQuantity);
        }

    }

}
