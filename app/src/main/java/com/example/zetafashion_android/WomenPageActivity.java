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

import com.example.zetafashion_android.Adapter.WomenAdapter;
import com.example.zetafashion_android.Model.WomenProducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WomenPageActivity extends AppCompatActivity {

    ImageView profile,cart;
    TextView title;

    RecyclerView rcView_WCategory;
    WomenAdapter womenAdapter;
    ArrayList<WomenProducts> womenProducts;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.women_page_layout);

        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);

        myRef = FirebaseDatabase.getInstance().getReference("Products");

        rcView_WCategory = findViewById(R.id.rcView_WCategory);
        rcView_WCategory.setLayoutManager(new LinearLayoutManager(this));
        womenProducts = new ArrayList<>();
        womenAdapter = new WomenAdapter(this,womenProducts);
        womenAdapter.notifyDataSetChanged();
        rcView_WCategory.setAdapter(womenAdapter);
        rcView_WCategory.setHasFixedSize(true);


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WomenPageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WomenPageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WomenPageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        GetWomenData();
    }

    private void GetWomenData() {
        myRef.orderByChild("ProductCategory").equalTo("Women").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WomenProducts womenProduct = dataSnapshot.getValue(WomenProducts.class);
                    womenProducts.add(womenProduct);
                    womenAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WomenPageActivity.this, "Error Loading Men Category Data Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}