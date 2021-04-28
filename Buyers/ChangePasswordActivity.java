package com.example.dsapp.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dsapp.Prevalent.Prevalent;
import com.example.dsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText phoneChangePassword, passwordChangePassword;
    private Button verifyChanges;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        
//        phoneChangePassword = findViewById(R.id.phone_change_password);
//        passwordChangePassword = findViewById(R.id.password_change_password);
//        verifyChanges = findViewById(R.id.verify_change_password_btn);
//
//        final String phoneChgPass = phoneChangePassword.getText().toString();
//        final String passChgPass = passwordChangePassword.getText().toString();
//
//        String getPhone = getIntent().getStringExtra("phone");
//
//
//        verifyChanges.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
//                        .child("Users")
//                        .child(phoneChgPass);
//
//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists())
//                        {
//                            if(snapshot.hasChild("Security Questions"))
//                            {
////                                String ans1 = snapshot.child("Security Questions").child("answer1").getValue().toString();
////                                String ans2 = snapshot.child("Security Questions").child("answer2").getValue().toString();
//
//                                if(!phoneChgPass.equals(""))
//                                {
//                                    ref.child("password").setValue(passChgPass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful())
//                                            {
//                                                Toast.makeText(ChangePasswordActivity.this, "Your password changed successfully", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
//                                                startActivity(intent);
//                                            }
//                                            else
//                                            {
//                                                Toast.makeText(ChangePasswordActivity.this, "it is not working", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }
//        });

    }
}
