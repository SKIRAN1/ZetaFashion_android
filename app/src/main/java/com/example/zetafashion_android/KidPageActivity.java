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
import com.example.zetafashion_android.Adapter.WomenAdapter;
import com.example.zetafashion_android.Model.KidProducts;
import com.example.zetafashion_android.Model.WomenProducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KidPageActivity extends AppCompatActivity {

    ImageView profile,cart;
    TextView title;

    RecyclerView rcView_KCategory;
    KidAdapter kidAdapter;
    ArrayList<KidProducts> kidProducts;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kid_page_layout);

        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);

        myRef = FirebaseDatabase.getInstance().getReference("Products");

        rcView_KCategory = findViewById(R.id.rcView_KCategory);
        rcView_KCategory.setLayoutManager(new LinearLayoutManager(this));
        kidProducts = new ArrayList<>();
        kidAdapter = new KidAdapter(this,kidProducts);
        kidAdapter.notifyDataSetChanged();
        rcView_KCategory.setAdapter(kidAdapter);
        rcView_KCategory.setHasFixedSize(true);


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KidPageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KidPageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KidPageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        GetKidData();

    }

    private void GetKidData() {
        myRef.orderByChild("ProductCategory").equalTo("Kid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    KidProducts kidProduct = dataSnapshot.getValue(KidProducts.class);
                    kidProducts.add(kidProduct);
                    kidAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(KidPageActivity.this, "Error Loading Men Category Data Try Again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}