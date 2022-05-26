package com.example.zetafashion_android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zetafashion_android.Admin.AdminHomepageActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginPageActivity extends AppCompatActivity {

    TextView tv_SignUp, tv_Admin;
    String user;
    EditText tf_Email;
    Button btn_login;
    ImageView btn_google;
    TextInputLayout et_Password, et_Email;
    private ProgressDialog loadingBar;
    private String dbName = "Users";
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    private static int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        btn_login = (Button) findViewById(R.id.loginButton);
        et_Email = (TextInputLayout) findViewById(R.id.et_Email);
        et_Password = (TextInputLayout) findViewById(R.id.et_Password);
        tv_SignUp = (TextView) findViewById(R.id.tV_SignUp);
        tv_Admin = (TextView) findViewById(R.id.tv_Admin);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        tf_Email = (EditText) findViewById(R.id.tf_Email);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        tv_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPageActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        tv_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPageActivity.this);
                builder.setTitle("Admin Login!");
                builder.setMessage("Enter Admin Password!");
                builder.setCancelable(false);
                final EditText input = new EditText(LoginPageActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);

                builder.setPositiveButton("LogIn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String password = input.getText().toString();
                        if (password.equals("000000")) {
                            Intent intent = new Intent(LoginPageActivity.this, AdminHomepageActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginPageActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setView(input);
                alertDialog.show();

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
//        if(user == "Customer") {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(LoginPageActivity.this, HomepageActivity.class);
            startActivity(intent);
//            }
//        }else if(user == "Admin") {
//                Intent intent = new Intent(MainActivity.this, AdminHomepageActivity.class);
//                startActivity(intent);
        }
    }

    private void LoginUser() {
        String email = et_Email.getEditText().getText().toString();
        String password = et_Password.getEditText().getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginPageActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginPageActivity.this, HomepageActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginPageActivity.this, "LogIn Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
