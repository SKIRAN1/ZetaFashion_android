package com.example.zetafashion_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zetafashion_android.Adapter.CartAdapter;
import com.example.zetafashion_android.Model.CartProducts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartpageActivity extends AppCompatActivity {

    String Phone;

    ImageView profile,cart;
    TextView title,tv_cartNumber;
    Button btn_checkout;

    RecyclerView rcView_Cart;
    CartAdapter cartAdapter;
    ArrayList<CartProducts> cartProducts;

    DatabaseReference databaseReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cartpage_layout);


        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
//        tv_cartNumber = findViewById(R.id.tv_cartNumber);
        btn_checkout = findViewById(R.id.btn_checkout);

        rcView_Cart = findViewById(R.id.rcView_Cart);
        rcView_Cart.setLayoutManager(new LinearLayoutManager(this));
        cartProducts = new ArrayList<>();
        cartAdapter = new CartAdapter(this,cartProducts);
     //   cartAdapter.notifyDataSetChanged();
        rcView_Cart.setAdapter(cartAdapter);
        rcView_Cart.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartpageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartpageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartpageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartProducts.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartpageActivity.this);
                    builder.setTitle("Alert!");
                    builder.setMessage("Cart is Empty!");
                    builder.setCancelable(true);
                    builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else {
                    Intent intent = new Intent(CartpageActivity.this, CheckoutPageActivity.class);
                    startActivity(intent);
                }
            }
        });

        CloningCartData();
    }

    public void CloningCartData(){
        databaseReference.orderByChild("Email").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   Phone = dataSnapshot.child("Phone").getValue().toString();
                }
                databaseReference.child(Phone).child("Cart Items").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            CartProducts cProduct = dataSnapshot.getValue(CartProducts.class);
                            cartProducts.add(cProduct);
                            cartAdapter.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(CartpageActivity.this, "Error, TryAgain!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartpageActivity.this, "Error, TryAgain!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}