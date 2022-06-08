package com.example.zetafashion_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.zetafashion_android.Adapter.HomeAdapter;
import com.example.zetafashion_android.Adapter.MenAdapter;
import com.example.zetafashion_android.Model.CartProducts;
import com.example.zetafashion_android.Model.MenProducts;
import com.example.zetafashion_android.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomepageActivity extends AppCompatActivity {


    ImageSlider imageSlider;
    SearchView searchView;
    ImageView profile,cart;
    TextView title,tv_cartNumber;
    DatabaseReference  myRef;

    CircleImageView btn_Men,btn_Women,btn_Kids,btn_Shoes,btn_Accessories;

    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    MenAdapter menAdapter;
    ArrayList<Products> products;
    ArrayList<CartProducts> cartProducts;
    ArrayList<MenProducts> menProducts;


    int images[] = {R.drawable.blue_blazer, R.drawable.blue_denim, R.drawable.classic_tshirt, R.drawable.green_tshirt,
                    R.drawable.red_shirt, R.drawable.skull_shirt, R.drawable.tiger_shirt, R.drawable.white_tshirt,
                    R.drawable.yellow_shirt};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);

        title = findViewById(R.id.title);
        searchView = findViewById(R.id.searchView);
        searchView.setVisibility(View.VISIBLE);
//        tv_cartNumber = findViewById(R.id.tv_cartNumber);
        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        imageSlider = findViewById(R.id.slider);

        btn_Men = (CircleImageView)findViewById(R.id.btn_Men);
        btn_Women = (CircleImageView) findViewById(R.id.btn_Women);
        btn_Kids = (CircleImageView) findViewById(R.id.btn_Kids);
        btn_Shoes = (CircleImageView) findViewById(R.id.btn_Shoes);
        btn_Accessories = (CircleImageView) findViewById(R.id.btn_Accessories);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<>();
        homeAdapter = new HomeAdapter(this,products);
        homeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setHasFixedSize(true);

        myRef = FirebaseDatabase.getInstance().getReference("Products");




        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.sliderimage1));
        slideModels.add(new SlideModel(R.drawable.sliderimage2));
        slideModels.add(new SlideModel(R.drawable.sliderimage3));
        slideModels.add(new SlideModel(R.drawable.sliderimage4));
        slideModels.add(new SlideModel(R.drawable.sliderimage5));
        slideModels.add(new SlideModel(R.drawable.sliderimage6));
        imageSlider.setImageList(slideModels, true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Products> filteredProducts = new ArrayList<>();
                for(Products product : products){
                    if(product.getProductName().toLowerCase().contains(s.toLowerCase())){
                        filteredProducts.add(product);
                    }
                }
                HomeAdapter adapter = new HomeAdapter(getApplicationContext(),filteredProducts);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        btn_Men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, MenPageActivity.class);
                startActivity(intent);
            }
        });

        btn_Women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, WomenPageActivity.class);
                startActivity(intent);
            }
        });

        btn_Kids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, KidPageActivity.class);
                startActivity(intent);
            }
        });

        btn_Shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, ShoePageActivity.class);
                startActivity(intent);
            }
        });

        btn_Accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, AccessoryPageActivity.class);
                startActivity(intent);
            }
        });

        GetProductData();
    }

//    private void CartNumberValidation() {
//
//        GetProductData();
//    }

    private void GetProductData() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Products product = dataSnapshot.getValue(Products.class);
                    products.add(product);
                    homeAdapter.notifyDataSetChanged();
                  //  product.setProductImageUrl(dataSnapshot.child("ProductImage").getValue().toString());
//                    product.setProductName(dataSnapshot.child("ProductName").getValue().toString());
//                    product.setProductPrice(dataSnapshot.child("ProductPrice").getValue().toString());
//                    product.setProductCategory(dataSnapshot.child("ProductCategory").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomepageActivity.this, "Error Loading Data Try Again!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}


