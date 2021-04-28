package com.example.dsapp.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {

    private Button sellerLoginBtn;
    private EditText emailInput, passwordInput;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        emailInput = findViewById(R.id.seller_login_email);
        passwordInput = findViewById(R.id.seller_login_password);
        sellerLoginBtn = findViewById(R.id.seller_login_btn);

        sellerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSeller();
            }
        });

    }

    private void loginSeller()
    {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(!email.equals("") && !password.equals(""))
        {
            loadingBar.setTitle("Sign In As Seller");
            loadingBar.setMessage("Please wait while checking");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        loadingBar.dismiss();
                        Toast.makeText(SellerLoginActivity.this, "You are logged in successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        loadingBar.dismiss();
                        Toast.makeText(SellerLoginActivity.this, "Username or password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Please fill out email and password", Toast.LENGTH_SHORT).show();
        }
    }
}
