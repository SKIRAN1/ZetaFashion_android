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

import com.example.zetafashion_android.Adapter.KidAdapter;
import com.example.zetafashion_android.Adapter.ShoeAdapter;
import com.example.zetafashion_android.Model.KidProducts;
import com.example.zetafashion_android.Model.ShoeProducts;
import com.example.zetafashion_android.Model.WomenProducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoePageActivity extends AppCompatActivity {
    ImageView profile,cart;
    TextView title;

    RecyclerView rcView_SCategory;
    ShoeAdapter shoeAdapter;
    ArrayList<ShoeProducts> shoeProducts;

    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoe_page_layout);

        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);

        myRef = FirebaseDatabase.getInstance().getReference("Products");

        rcView_SCategory = findViewById(R.id.rcView_SCategory);
        rcView_SCategory.setLayoutManager(new LinearLayoutManager(this));
        shoeProducts = new ArrayList<>();
        shoeAdapter = new ShoeAdapter(this,shoeProducts);
        shoeAdapter.notifyDataSetChanged();
        rcView_SCategory.setAdapter(shoeAdapter);
        rcView_SCategory.setHasFixedSize(true);


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoePageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoePageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoePageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        GetShoeData();
    }

    private void GetShoeData() {
        myRef.orderByChild("ProductCategory").equalTo("Shoe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ShoeProducts shoeProduct = dataSnapshot.getValue(ShoeProducts.class);
                    shoeProducts.add(shoeProduct);
                    shoeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShoePageActivity.this, "Error Loading Men Category Data Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}