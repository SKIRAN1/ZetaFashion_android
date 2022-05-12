package com.example.zetafashion_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView clickable_text;
    EditText tf_Email;
    Button btn_login;
    ImageView btn_google;
    //    GoogleSignInClient mGoogleSignInClient;
    TextInputLayout et_Password, et_Email;
    private ProgressDialog loadingBar;
    private String dbName = "Users";
    private FirebaseAuth mAuth;


    //  private static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        btn_login = (Button) findViewById(R.id.loginButton);
        btn_google = (ImageView) findViewById(R.id.btn_google);
        et_Email = (TextInputLayout) findViewById(R.id.et_Email);
        et_Password = (TextInputLayout) findViewById(R.id.et_Password);
        clickable_text = (TextView) findViewById(R.id.textView3);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        tf_Email = (EditText) findViewById(R.id.tf_Email);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser();


            }
        });

        clickable_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

//        btn_google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });


    }

//    private void CheckUser() {
//        String email = et_Email.getEditText().getText().toString();
//        String password = et_Password.getEditText().getText().toString();
//        if (email == "admin") {
//            if (password == "000000") {
//                Intent intent = new Intent(MainActivity.this, AdminpageActivity.class);
//                startActivity(intent);
//            }
//        }
//        else{
//
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
            startActivity(intent);
        }
    }

    private void LoginUser()
    {
        String email = et_Email.getEditText().getText().toString();
        String password = et_Password.getEditText().getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
        }

        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "LogIn Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


//    private void LoginUser()
//    {
//        String email = et_Email.getText().toString();
//        String password = et_Password.getText().toString();
//
//     if(TextUtils.isEmpty(email))
//     {
//        Toast.makeText(this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
//     }
//     else if(TextUtils.isEmpty(password))
//     {
//         Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
//     }
//     else
//     {
//         loadingBar.setTitle("Logging Account");
//         loadingBar.setMessage("Please wait until we are checking credentials");
//         loadingBar.setCanceledOnTouchOutside(false);
//         loadingBar.show();
//
//         AllowAccessToAccount(email, password);
//     }
//
//
//
//
//    }
//
//    private void AllowAccessToAccount(String email, String password)
//    {
//        final DatabaseReference myRef;
//        myRef = FirebaseDatabase.getInstance().getReference();
//
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if(dataSnapshot.child(dbName).child(email).exists())
//                {
//                    Users usersData = dataSnapshot.child(dbName).child(email).getValue(Users.class);
//                    if(usersData.getEmail().equals(email))
//                    {
//                        if(usersData.getPassword().equals(password))
//                        {
//                            Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
//                            loadingBar.dismiss();
//                            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
//                            startActivity(intent);
//                        }
//                        else
//                        {
//                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                }else
//                {
//                    Toast.makeText(MainActivity.this, "No Account Exist", Toast.LENGTH_SHORT).show();
//                    loadingBar.dismiss();
//                }
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error)
//            {
//
//            }
//        });
//    }
//    public void signIn(){
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//            if(acct != null){
//                String name = acct.getDisplayName();
//                Uri pic = acct.getPhotoUrl();
//                String email = acct.getEmail();
//
//
//                Toast.makeText(this,"UserEmail:" +email, Toast.LENGTH_SHORT).show();
//            }
//
//            startActivity(new Intent(MainActivity.this, HomepageActivity.class));
//            // Signed in successfully, show authenticated UI.
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.d("message" , e.toString());
//
//        }
//    }
}