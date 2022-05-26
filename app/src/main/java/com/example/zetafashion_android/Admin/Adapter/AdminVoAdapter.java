package com.example.zetafashion_android.Admin.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zetafashion_android.Model.Orders;
import com.example.zetafashion_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminVoAdapter extends RecyclerView.Adapter<AdminVoAdapter.MyViewHolder> {
    Context context;
    ArrayList<Orders> orders;
    private ProgressDialog loadingBar;

    DatabaseReference databaseReference;


    public AdminVoAdapter(Context context, ArrayList<Orders> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_item_order, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        loadingBar = new ProgressDialog(context);
        Orders order = orders.get(position);
        holder.customerName.setText(order.getName());
        holder.customerEmail.setText(order.getEmail());
        holder.customerPhone.setText(order.getPhone());
        holder.customerAddress.setText(order.getAddress());
        databaseReference = FirebaseDatabase.getInstance().getReference();

        holder.btn_orderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Cancelling Order");
                loadingBar.setMessage("Please wait until we are cancelling order");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                        databaseReference.child("Orders").child(holder.customerPhone.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT).show();
                                holder.notify();
                                loadingBar.dismiss();
                            }
                        });
                    }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView customerName,customerEmail,customerPhone,customerAddress;
        Button btn_orderCancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            customerName = itemView.findViewById(R.id.customer_Name);
            customerEmail = itemView.findViewById(R.id.customer_Email);
            customerAddress = itemView.findViewById(R.id.customer_Address);
            customerPhone = itemView.findViewById(R.id.customer_Phone);
            btn_orderCancel = itemView.findViewById(R.id.btn_orderCancel);
        }
    }
}
