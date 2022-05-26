package com.example.zetafashion_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilepageActivity extends AppCompatActivity {

    TextInputLayout et_userAccountEmail, et_userAccountPassword, et_userAccountPhone;
    EditText tf_userAccountEmail,tf_userAccountPhone,tf_userAccountPassword;
    TextView userAccountName,title;
    Button btn_signOut;
    String Phone;
    ImageView profile,cart,profilePic;

    private FirebaseUser user;
    private DatabaseReference myRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilepage_layout);

        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        profilePic = findViewById(R.id.circular_pic);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilepageActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilepageActivity.this, ProfilepageActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilepageActivity.this, CartpageActivity.class);
                startActivity(intent);
            }
        });
        et_userAccountEmail = (TextInputLayout) findViewById(R.id.et_userAccountEmail);
        userAccountName = (TextView) findViewById(R.id.userAccountName);
        et_userAccountPassword = (TextInputLayout) findViewById(R.id.et_userAccountPassword);
        et_userAccountPhone = (TextInputLayout) findViewById(R.id.et_userAccountPhone);
        btn_signOut = (Button) findViewById(R.id.btn_signOut);
        tf_userAccountEmail = (EditText) findViewById(R.id.tf_userAccountEmail);
        tf_userAccountPhone = (EditText) findViewById(R.id.tf_userAccountPhone);
        tf_userAccountPassword = (EditText) findViewById(R.id.tf_userAccountPassword);

        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

//        profilePic.setImageURI(user.getPhotoUrl());


        myRef.orderByChild("Email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){
                 String  name = ""+ds.child("Name").getValue();
                  String email = ""+ds.child("Email").getValue();
                  String phone = ""+ds.child("Phone").getValue();
                  String password = ""+ds.child("Password").getValue();
                  Phone = ds.child("Phone").getValue().toString();
                  userAccountName.setText(name);
                  tf_userAccountEmail.setText(email);
                  tf_userAccountPhone.setText(phone);
                  tf_userAccountPassword.setText(password);
                    Toast.makeText(ProfilepageActivity.this, Phone, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilepageActivity.this, "Error! Please try again", Toast.LENGTH_SHORT).show();
            }
        });

        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogout();
            }
        });

    }

    private void UserLogout()
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ProfilepageActivity.this, LoginPageActivity.class);
        startActivity(intent);
    }
}