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
    String email,password,phone,name;

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
    private Boolean ValidateName() {

        name = et_userName.getEditText().getText().toString();

        if (TextUtils.isEmpty(name)) {
            et_userName.setError("Name is Required");
            return false;
        }else{
            et_userName.setError(null);
            et_userName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean ValidateEmail() {
        email = et_userEmail.getEditText().getText().toString();

        if (TextUtils.isEmpty(email)) {
            et_userEmail.setError("Email is Required");
            return false;
        }else if(!(Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            et_userEmail.setError("Enter Valid email");
            return false;
        }else{
            et_userEmail.setError(null);
            et_userEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean ValidatePhone() {

        phone = et_userPhone.getEditText().getText().toString();

        if (TextUtils.isEmpty(phone)) {
            et_userPhone.setError("Phone Number is Required");
            return false;
        }else if(phone.length() < 10){
            et_userPhone.setError("PhoneNumber is not valid");
            return  false;
        }
        else{
            et_userPhone.setError(null);
            et_userPhone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean ValidatePassword() {

        password = et_userPassword.getEditText().getText().toString();


        if (TextUtils.isEmpty(password)) {
            et_userPassword.setError("Address is Required");
            return false;
        }else if(password.length() < 6){
            et_userPassword.setError("Password is too short!");
            return false;
        }
        else{
            et_userPassword.setError(null);
            et_userPassword.setErrorEnabled(false);
            return true;
        }
    }

    private void createAccount() {


        if(!ValidateName() | !ValidateEmail()  | !ValidatePassword() | !ValidatePhone()){
            return;
        }
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait until we are checking credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(name,phone,email,password);
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


