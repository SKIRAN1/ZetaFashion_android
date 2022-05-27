package com.example.zetafashion_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zetafashion_android.Adapter.AccessoryAdapter;
import com.example.zetafashion_android.Adapter.KidAdapter;
import com.example.zetafashion_android.Model.AccessoryProducts;
import com.example.zetafashion_android.Model.KidProducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccessoryPageActivity extends AppCompatActivity {
    ImageView profile,cart;
    TextView title;

    RecyclerView rcView_ACategory;
    AccessoryAdapter accessoryAdapter;
    ArrayList<AccessoryProducts> accessoryProducts;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessory_page_layout);

        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);

        myRef = FirebaseDatabase.getInstance().getReference("Products");

        rcView_ACategory = findViewById(R.id.rcView_ACategory);
        rcView_ACategory.setLayoutManager(new LinearLayoutManager(this));
        accessoryProducts = new ArrayList<>();
        accessoryAdapter = new AccessoryAdapter(this,accessoryProducts);
        accessoryAdapter.notifyDataSetChanged();
        rcView_ACategory.setAdapter(accessoryAdapter);
        rcView_ACategory.setHasFixedSize(true);


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccessoryPageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccessoryPageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccessoryPageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        GetAccessoryData();
    }

    private void GetAccessoryData() {
        myRef.orderByChild("ProductCategory").equalTo("Accessory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AccessoryProducts accessoryProduct = dataSnapshot.getValue(AccessoryProducts.class);
                    accessoryProducts.add(accessoryProduct);
                    accessoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccessoryPageActivity.this, "Error Loading Men Category Data Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}