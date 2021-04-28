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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SellerRegisterationActivity extends AppCompatActivity {

    private Button sellerLoginBegin, registerButton;
    private EditText nameInput, phoneInput, emailInput, passwordInput, addressInput;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registeration);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        sellerLoginBegin = findViewById(R.id.seller_have_already_account_btn);
        registerButton = findViewById(R.id.seller_register_btn);
        nameInput = findViewById(R.id.name_seller);
        phoneInput = findViewById(R.id.phone_seller);
        emailInput = findViewById(R.id.email_seller);
        passwordInput = findViewById(R.id.password_seller);
        addressInput = findViewById(R.id.address_seller);




        
        sellerLoginBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerRegisterationActivity.this, SellerLoginActivity.class);
                startActivity(intent);
            }
        });
        
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });

    }

    private void registerSeller() 
    {
        final String name = nameInput.getText().toString();
        final String phone = phoneInput.getText().toString();
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();
        final String address = addressInput.getText().toString();

        if(!name.equals("") && !phone.equals("") && !email.equals("") && !address.equals("") && !password.equals(""))
        {

            loadingBar.setTitle("Create Seller Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        final DatabaseReference Ref;
                        Ref = FirebaseDatabase.getInstance().getReference();
                        String sid = mAuth.getCurrentUser().getUid();
                        HashMap<String,Object> sellerMap = new HashMap<>();
                        sellerMap.put("sid", sid);
                        sellerMap.put("phone", phone);
                        sellerMap.put("email", email);
                        sellerMap.put("address", address);
                        sellerMap.put("name", name);

                        Ref.child("Sellers").child(sid).updateChildren(sellerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                            loadingBar.dismiss();
                                            Toast.makeText(SellerRegisterationActivity.this, "you are registered successfully", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(SellerRegisterationActivity.this, SellerHomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                    }
                                });
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Please fill out all the form", Toast.LENGTH_SHORT).show();
        }
        
    }
}
