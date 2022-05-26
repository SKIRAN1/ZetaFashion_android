package com.example.zetafashion_android.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zetafashion_android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminAPpageActivity extends AppCompatActivity {

    Button btn_cancel,btn_add;
    TextInputLayout et_productName,et_productPrice,et_productCategory,et_productImage;
    private ProgressDialog loadingBar;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_appage_layout);

        btn_cancel = findViewById(R.id.btn_Cancel);
        btn_add = findViewById(R.id.btn_Add);
        et_productCategory =(TextInputLayout) findViewById(R.id.et_productCategory);
        et_productName = (TextInputLayout) findViewById(R.id.et_productName);
        et_productPrice =(TextInputLayout) findViewById(R.id.et_productPrice);
        et_productImage = (TextInputLayout) findViewById(R.id.et_productImage);
        loadingBar = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");



        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AddingProduct();
                }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAPpageActivity.this, AdminHomepageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AddingProduct() {

        String productName = et_productName.getEditText().getText().toString();
        String productCategory = et_productCategory.getEditText().getText().toString();
        String productPrice = et_productPrice.getEditText().getText().toString();
        String productImage = et_productImage.getEditText().getText().toString();

        if(TextUtils.isEmpty(productName)){
            Toast.makeText(AdminAPpageActivity.this, "Please Enter Product Name!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(productCategory)){
            Toast.makeText(AdminAPpageActivity.this, "Please Enter Product Category", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(productPrice)){
            Toast.makeText(AdminAPpageActivity.this, "Please Enter Product Price", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(productImage)){
            Toast.makeText(AdminAPpageActivity.this, "Please Enter Product Image Url", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Adding Product");
            loadingBar.setMessage("Please wait until we are adding to dataBase");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!(dataSnapshot.child(productName).exists())) {
                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("ProductName", productName);
                        userdataMap.put("ProductPrice", productPrice);
                        userdataMap.put("ProductCategory", productCategory);
                        userdataMap.put("ProductImageUrl", productImage);

                        databaseReference.child(productName).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AdminAPpageActivity.this, "Product Added Successfully!", Toast.LENGTH_SHORT).show();
                                    et_productName.getEditText().setText("");
                                    et_productCategory.getEditText().setText("");
                                    et_productPrice.getEditText().setText("");
                                    et_productImage.getEditText().setText("");
                                    loadingBar.dismiss();
                                }
                            }
                        });

                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(AdminAPpageActivity.this, "A product with this " + productName + " name already exists", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AdminAPpageActivity.this, "Error Occurred, Try Again!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}