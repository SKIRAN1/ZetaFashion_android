package com.example.zetafashion_android.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zetafashion_android.Admin.Adapter.AdminVoAdapter;
import com.example.zetafashion_android.Model.Orders;
import com.example.zetafashion_android.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminVOpageActivity extends AppCompatActivity {

    private Button btn_goHome;

    RecyclerView rcView_orders;
    AdminVoAdapter adminVoAdapter;
    ArrayList<Orders> orders;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_vopage_layout);

        btn_goHome = findViewById(R.id.btn_canncel);
        rcView_orders = findViewById(R.id.rcView_orders);
        rcView_orders.setLayoutManager(new LinearLayoutManager(this));
        orders = new ArrayList<>();
        adminVoAdapter = new AdminVoAdapter(this,orders);
        adminVoAdapter.notifyDataSetChanged();
        rcView_orders.setAdapter(adminVoAdapter);
        rcView_orders.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminVOpageActivity.this, AdminHomepageActivity.class);
                startActivity(intent);
            }
        });

      GetOrderData();

    }

    private void GetOrderData() {
        databaseReference.child("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Orders order = dataSnapshot.getValue(Orders.class);
                    orders.add(order);
                    adminVoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}