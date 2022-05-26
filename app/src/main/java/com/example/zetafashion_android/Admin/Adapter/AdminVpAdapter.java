package com.example.zetafashion_android.Admin.Adapter;

import android.app.ProgressDialog;
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
import com.example.zetafashion_android.Model.Products;
import com.example.zetafashion_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminVpAdapter extends RecyclerView.Adapter<AdminVpAdapter.MyViewHolder> {

    Context context;
    ArrayList<Products> items;
    private ProgressDialog loadingBar;
    DatabaseReference databaseReference;


    public AdminVpAdapter(Context context, ArrayList<Products> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_admin_delete, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products item = items.get(position);
        loadingBar = new ProgressDialog(context);
        holder.productName.setText(item.getProductName());
        holder.productPrice.setText(item.getProductPrice());
        Glide.with(context).load(item.getProductImageUrl()).into(holder.productImage);

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Adding Product");
                loadingBar.setMessage("Please wait until we are deleting from dataBase");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                databaseReference = FirebaseDatabase.getInstance().getReference("Products");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(item.getProductName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context, "Product Removed", Toast.LENGTH_SHORT).show();
                                    holder.notify();
                                    loadingBar.dismiss();
                                }
                                else{
                                    loadingBar.dismiss();
                                    Toast.makeText(context, "Error, Try Again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        loadingBar.dismiss();
                        Toast.makeText(context, "Error, Try Again!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice;
        ImageView productImage;
        Button btn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.pro_Name);
            productPrice = itemView.findViewById(R.id.pro_price);
            productImage = itemView.findViewById(R.id.pro_image);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
