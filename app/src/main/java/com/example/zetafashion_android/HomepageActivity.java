package com.example.zetafashion_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.denzcoskun.imageslider.ImageSlider;
//import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;


public class HomepageActivity extends AppCompatActivity {

//    ImageSlider imageSlider;
    ImageView profile,cart;
    TextView title;

    RecyclerView recyclerView;

//    int images[] = {R.drawable.blue_blazer, R.drawable.blue_denim,R.drawable.classic_tshirt,R.drawable.green_tshirt,
//            R.drawable.red_shirt,R.drawable.skull_shirt,R.drawable.tiger_shirt,R.drawable.white_tshirt,
//            R.drawable.yellow_shirt};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);

        title = findViewById(R.id.title);
        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
//        imageSlider = findViewById(R.id.slider);
        recyclerView = findViewById(R.id.recyclerView);

//        MyAdapter myAdapter = new MyAdapter(this , images);
//        recyclerView.setAdapter(myAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        List<SlideModel> slideModels = new ArrayList<>();
//        slideModels.add(new SlideModel(R.drawable.sliderimage1));
//        slideModels.add(new SlideModel(R.drawable.sliderimage2));
//        slideModels.add(new SlideModel(R.drawable.sliderimage3));
//        slideModels.add(new SlideModel(R.drawable.sliderimage4));
//        slideModels.add(new SlideModel(R.drawable.sliderimage5));
//        slideModels.add(new SlideModel(R.drawable.sliderimage6));
//        imageSlider.setImageList(slideModels, true);


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







////     queue = Volley.newRequestQueue(this);
////
////        Button button = (Button) findViewById(R.id.button);
////        button.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(HomepageActivity.this, ProfilepageActivity.class);
////                startActivity(intent);
////            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}


