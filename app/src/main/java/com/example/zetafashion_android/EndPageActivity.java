package com.example.zetafashion_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndPageActivity extends AppCompatActivity {

    ImageView profile,cart;
    TextView title;
    Button btn_done;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_page_layout);

        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        btn_done = findViewById(R.id.btn_done);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndPageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndPageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EndPageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EndPageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });
    }
}