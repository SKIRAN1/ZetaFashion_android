package com.example.zetafashion_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText et_userEmail,et_userPassword,et_userName,et_userPhone;
    Button btn_signUp;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.signup_layout);

        et_userEmail = (EditText) findViewById(R.id.et_userEmail);
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_userPassword = (EditText) findViewById(R.id.et_userPassword);
        et_userPhone = (EditText) findViewById(R.id.et_userPhone);
        btn_signUp = (Button) findViewById(R.id.btn_signUp);
        loadingBar = new ProgressDialog(this);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount()
    {
        String name = et_userName.getText().toString();
        String phone = et_userPhone.getText().toString();
        String password = et_userPassword.getText().toString();
        String email = et_userEmail.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Please Enter Your Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this,"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait until we are checking credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            
            ValidateEmail(name,phone,email,password);

        }


    }

    private void ValidateEmail(String name, String phone, String email, String password)
    {
       final DatabaseReference RootRef;
       RootRef = FirebaseDatabase.getInstance().getReference();

       RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot)
           {
               if(!(snapshot.child("Users").child(email).exists()))
               {
                   HashMap<String, Object> userdataMap = new HashMap<>();
                   userdataMap.put("Email", email);
                   userdataMap.put("Phone", phone);
                   userdataMap.put("Name", name);
                   userdataMap.put("Password", password);

               //    RootRef.child("Users").child(email).updateChildren(userdataMap);


               }
               else
               {
                   Toast.makeText(SignupActivity.this,"This" + email + "already exists" ,Toast.LENGTH_SHORT).show();
                   loadingBar.dismiss();

                   Intent intent = new Intent(SignupActivity.this, MainActivity.class);
////                startActivity(intent);
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }
}