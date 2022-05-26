package com.example.zetafashion_android.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zetafashion_android.Admin.Adapter.AdminVpAdapter;
import com.example.zetafashion_android.Model.Products;
import com.example.zetafashion_android.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminVPpageActivity extends AppCompatActivity {
    Button btn_goHome;
    TextView tv_Products;


    RecyclerView vp_RecyclerView;
    AdminVpAdapter adminVpAdapter;
    ArrayList<Products> products;

    DatabaseReference databaseReference,myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_vppage_layout);

        btn_goHome = findViewById(R.id.btn_goHome);
        tv_Products = findViewById(R.id.tv_Products);


        vp_RecyclerView = findViewById(R.id.vp_RecyclerView);
        vp_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<>();
        adminVpAdapter = new AdminVpAdapter(this,products);
        adminVpAdapter.notifyDataSetChanged();
        vp_RecyclerView.setAdapter(adminVpAdapter);
        vp_RecyclerView.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        btn_goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminVPpageActivity.this, AdminHomepageActivity.class);
                startActivity(intent);
            }
        });

        tv_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminVPpageActivity.this, AdminVPpageActivity.class);
                startActivity(intent);
            }
        });


        CloningProducts();

    }


    private void CloningProducts() {


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Products product = dataSnapshot.getValue(Products.class);
                    products.add(product);
                    adminVpAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminVPpageActivity.this, "Error, Try Again!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}