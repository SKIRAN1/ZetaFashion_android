package com.example.zetafashion_android.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zetafashion_android.LoginPageActivity;
import com.example.zetafashion_android.R;

public class AdminHomepageActivity extends AppCompatActivity {

    Button btn_viewProducts,btn_addProduct, btn_viewOrders,btn_logOutAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage_layout);

        btn_viewProducts = findViewById(R.id.btn_viewProducts);
        btn_addProduct = findViewById(R.id.btn_addProduct);
        btn_viewOrders = findViewById(R.id.btn_viewOrders);
        btn_logOutAdmin = findViewById(R.id.btn_logOutAdmin);


        btn_logOutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomepageActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });


        btn_viewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomepageActivity.this, AdminVPpageActivity.class);
                startActivity(intent);
            }
        });

        btn_viewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomepageActivity.this, AdminVOpageActivity.class);
                startActivity(intent);
            }
        });

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomepageActivity.this, AdminAPpageActivity.class);
                startActivity(intent);
            }
        });
    }
}