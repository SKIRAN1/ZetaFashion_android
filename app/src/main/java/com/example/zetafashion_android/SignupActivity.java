package com.example.zetafashion_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    TextInputLayout et_userEmail, et_userPassword, et_userName, et_userPhone;
    Button btn_signUp;
    private ProgressDialog loadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_layout);

        et_userEmail = (TextInputLayout) findViewById(R.id.et_userEmail);
        et_userName = (TextInputLayout) findViewById(R.id.et_userName);
        et_userPassword = (TextInputLayout) findViewById(R.id.et_userPassword);
        et_userPhone = (TextInputLayout) findViewById(R.id.et_userPhone);
        btn_signUp = (Button) findViewById(R.id.btn_signUp);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String password = et_userPassword.getEditText().getText().toString();
        String email = et_userEmail.getEditText().getText().toString();
        String name = et_userName.getEditText().getText().toString();
        String phone = et_userPhone.getEditText().getText().toString();


        if (TextUtils.isEmpty(email)) {
            //   Toast.makeText(this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
            et_userEmail.setError("Email is Required");
            et_userEmail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_userEmail.setError("Please Provide valid Email");
            et_userEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            //  Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
            et_userPassword.setError("Password is Required");
            et_userPassword.requestFocus();
        } else if (password.length() < 6) {
            et_userPassword.setError("Password should be at least 6 characters!");
            et_userPassword.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            //  Toast.makeText(this,"Please Enter Your Name",Toast.LENGTH_SHORT).show();
            et_userName.setError("Name is Required");
            et_userName.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
         //   Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            et_userPhone.setError("PhoneNumber is Required");
            et_userPhone.requestFocus();
        } else{
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait until we are checking credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(name,phone,email,password);
        }

    }
    private void ValidateEmail(String name, String phone, String email, String password) {
        final DatabaseReference myRef;
        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Email", email);
                    userdataMap.put("Phone", phone);
                    userdataMap.put("Name", name);
                    userdataMap.put("Password", password);

                    myRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(SignupActivity.this, "Congratulations your account is created", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();

                                                    Intent intent = new Intent(SignupActivity.this, LoginPageActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });

                                    } else {
                                        loadingBar.dismiss();
                                        Toast.makeText(SignupActivity.this, "Error, Please try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(SignupActivity.this, "This" + phone + "already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent intent = new Intent(SignupActivity.this, LoginPageActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(SignupActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

            }
        });
    }
}


