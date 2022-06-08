package com.example.zetafashion_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zetafashion_android.Adapter.MenAdapter;
import com.example.zetafashion_android.Model.MenProducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MenPageActivity extends AppCompatActivity {

    ImageView profile,cart;
    TextView title;
    RecyclerView rcView_Category;
    MenAdapter menAdapter;
    ArrayList<MenProducts> menProducts;
    Button btn_highPrice, btn_lowPrice;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.men_page_layout);

        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        btn_highPrice = findViewById(R.id.btn_highPrice);
        btn_lowPrice = findViewById(R.id.btn_lowPrice);
        rcView_Category = findViewById(R.id.rcView_Category);
        rcView_Category.setLayoutManager(new LinearLayoutManager(this));
        menProducts = new ArrayList<>();
        menAdapter = new MenAdapter(this, menProducts);
        menAdapter.notifyDataSetChanged();
        rcView_Category.setAdapter(menAdapter);
        rcView_Category.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference("Products");

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenPageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenPageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenPageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        btn_highPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(menProducts, MenProducts.ProductPriceHLComparator);
                menAdapter.notifyDataSetChanged();
                Toast.makeText(MenPageActivity.this, "Sort High to Low Price", Toast.LENGTH_SHORT).show();
            }
        });

        btn_lowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(menProducts, MenProducts.ProductPriceLHComparator);
                menAdapter.notifyDataSetChanged();
                Toast.makeText(MenPageActivity.this, "Sort Low to HIgh Price", Toast.LENGTH_SHORT).show();
            }
        });

        GetMenData();
    }

    private void GetMenData() {
        myRef.orderByChild("ProductCategory").equalTo("Men").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MenProducts categoryProduct = dataSnapshot.getValue(MenProducts.class);
                    menProducts.add(categoryProduct);
                    menAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenPageActivity.this, "Error Loading Men Category Data Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}