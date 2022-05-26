package com.example.zetafashion_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zetafashion_android.Model.CartProducts;
import com.example.zetafashion_android.Model.CheckoutProducts;
import com.example.zetafashion_android.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyViewHolder>{
    Context context;
    ArrayList<CheckoutProducts> checkoutProducts;


    public CheckoutAdapter(Context context, ArrayList<CheckoutProducts> checkoutProducts) {
        this.context = context;
        this.checkoutProducts = checkoutProducts;
    }

    @NonNull
    @Override
    public CheckoutAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_checkout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.MyViewHolder holder, int position) {
        CheckoutProducts checkProduct = checkoutProducts.get(position);
        holder.productName.setText(checkProduct.getProductName());
        holder.productPrice.setText(checkProduct.getProductPrice());
        holder.tv_QtyNumber.setText(checkProduct.getProductQuantity());
        Glide.with(context).load(checkProduct.getProductImageUrl()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return checkoutProducts.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView productName,productPrice,tv_QtyNumber;
        ImageView productImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.pro_Name);
            productPrice = itemView.findViewById(R.id.pro_price);
            productImage = itemView.findViewById(R.id.pro_image);
            tv_QtyNumber = itemView.findViewById(R.id.tv_QtyNumber);
        }

    }
}
