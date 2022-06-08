package com.example.zetafashion_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zetafashion_android.Adapter.CartAdapter;
import com.example.zetafashion_android.Adapter.CheckoutAdapter;
import com.example.zetafashion_android.Model.CartProducts;
import com.example.zetafashion_android.Model.CheckoutProducts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class CheckoutPageActivity extends AppCompatActivity {

    ImageView profile,cart;
    TextView title;
    Button btn_pay;
    ProgressDialog loadingBar;
    RecyclerView rcView_Checkout;
    CheckoutAdapter checkoutAdapter;
    ArrayList<CheckoutProducts> checkoutProducts;

    TextInputLayout et_checkoutName,et_checkoutPhone,et_checkoutAddress,et_checkoutEmail;

    DatabaseReference databaseReference;
    FirebaseUser user;
    String Phone,name,email,phone,address,ProductName,ProductPrice,ProductCategory,ProductQuantity,ProductImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_page_layout);

        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        btn_pay = findViewById(R.id.btn_pay);
        loadingBar = new ProgressDialog(this);

        et_checkoutEmail = (TextInputLayout) findViewById(R.id.et_checkoutEmail);
        et_checkoutName = findViewById(R.id.et_checkoutName);
        et_checkoutAddress = findViewById(R.id.et_checkoutAddress);
        et_checkoutPhone = findViewById(R.id.et_checkoutPhone);

        rcView_Checkout = findViewById(R.id.rcView_Checkout);
        rcView_Checkout.setLayoutManager(new LinearLayoutManager(this));
        checkoutProducts = new ArrayList<>();
        checkoutAdapter = new CheckoutAdapter(this,checkoutProducts);

        rcView_Checkout.setAdapter(checkoutAdapter);
        checkoutAdapter.notifyDataSetChanged();
        rcView_Checkout.setHasFixedSize(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutPageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutPageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutPageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailConfirmation();
            }
        });

        GetCheckoutProduct();
    }

    private Boolean ValidateName() {

        name = et_checkoutName.getEditText().getText().toString();

        if (TextUtils.isEmpty(name)) {
            et_checkoutName.setError("Name is Required");
            return false;
        }else{
            et_checkoutName.setError(null);
            et_checkoutName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean ValidateEmail() {
        email = et_checkoutEmail.getEditText().getText().toString();

        if (TextUtils.isEmpty(email)) {
            et_checkoutEmail.setError("Email is Required");
            return false;
        }else if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            et_checkoutEmail.setError("Enter Valid email");
            return false;
        }else{
            et_checkoutEmail.setError(null);
            et_checkoutEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean ValidatePhone() {

        phone = et_checkoutPhone.getEditText().getText().toString();

        if (TextUtils.isEmpty(phone)) {
            et_checkoutPhone.setError("Phone Number is Required");
            return false;
        }else if(phone.length() < 10){
            et_checkoutPhone.setError("PhoneNumber is not valid");
            return  false;
        }
        else{
            et_checkoutPhone.setError(null);
            et_checkoutPhone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean ValidateAddress() {

        address = et_checkoutAddress.getEditText().getText().toString();


        if (TextUtils.isEmpty(address)) {
            et_checkoutAddress.setError("Address is Required");
            return false;
        }else if(address.length() < 10){
            et_checkoutAddress.setError("Address is too Short");
            return false;
        }
        else{
            et_checkoutAddress.setError(null);
            et_checkoutAddress.setErrorEnabled(false);
            return true;
        }
    }

    private void GetCheckoutProduct() {
        databaseReference.child("Users").orderByChild("Email").equalTo(user.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Phone = dataSnapshot.child("Phone").getValue().toString();
                    et_checkoutEmail.getEditText().setText(dataSnapshot.child("Email").getValue().toString());
                    et_checkoutName.getEditText().setText(dataSnapshot.child("Name").getValue().toString());
                    et_checkoutPhone.getEditText().setText(Phone);
                }
                databaseReference.child("Users").child(Phone).child("Cart Items").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            CheckoutProducts checkProduct = dataSnapshot.getValue(CheckoutProducts.class);
                            checkoutProducts.add(checkProduct);
                            checkoutAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(CheckoutPageActivity.this, "Error, TryAgain!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CheckoutPageActivity.this, "Error, TryAgain!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void EmailConfirmation() {
        if(!ValidateName() | !ValidateEmail()  | !ValidateAddress() | !ValidatePhone()){
            return;
        }
        loadingBar.setTitle("Authorizing Payment");
        loadingBar.setMessage("Confirmation Email is on the way");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                loadingBar.dismiss();
                Intent intent = new Intent(CheckoutPageActivity.this, EndPageActivity.class);
                startActivity(intent);
                CheckoutPageActivity.this.finish();
            }
        }, 5000);

        PostingOrder();
    }

    private void PostingOrder() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long q = snapshot.child("Orders").getChildrenCount();
                HashMap<String, Object> userdataMap = new HashMap<>();
                userdataMap.put("Email", email);
                userdataMap.put("Phone", phone);
                userdataMap.put("Name", name);
                userdataMap.put("Address", address);
                databaseReference.child("Orders").child(Phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            databaseReference.child("Users").child(Phone).child("Cart Items").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            ProductName = dataSnapshot.child("ProductName").getValue().toString();
                                            ProductPrice = dataSnapshot.child("ProductPrice").getValue().toString();
                                            ProductImageUrl = dataSnapshot.child("ProductImageUrl").getValue().toString();
                                            ProductCategory = dataSnapshot.child("ProductCategory").getValue().toString();
                                            ProductQuantity = dataSnapshot.child("ProductQuantity").getValue().toString();
                                        }
                                        HashMap<String, Object> userdataMap2 = new HashMap<>();
                                        userdataMap2.put("ProductName", ProductName);
                                        userdataMap2.put("ProductCategory", ProductCategory);
                                        userdataMap2.put("ProductPrice", ProductPrice);
                                        userdataMap2.put("ProductImageUrl", ProductImageUrl);
                                        userdataMap2.put("ProductQuantity", ProductQuantity);
                                        databaseReference.child("Orders").child(Phone).child("Cart Items").child(ProductName).updateChildren(userdataMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(CheckoutPageActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}