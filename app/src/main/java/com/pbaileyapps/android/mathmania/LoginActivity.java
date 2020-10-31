package com.pbaileyapps.android.mathmania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private EditText email,password;
    private Button login,signup;
    private String keyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                firebaseDatabase = FirebaseDatabase.getInstance();
                if(emailString.isEmpty() || passwordString.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please enter in your email and password to sign up",Toast.LENGTH_LONG).show();
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //register user in database
                                        Toast.makeText(LoginActivity.this,"User registered",Toast.LENGTH_LONG).show();
                                        String keyId = firebaseAuth.getUid();
                                        assert keyId != null;
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("Score", "None");
                                        firebaseDatabase.getReference().child(keyId).child("Email").setValue(emailString);


                                    }
                                    else{
                                        task.getException().printStackTrace();
                                        Toast.makeText(LoginActivity.this,"User not registered",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                if(emailString.isEmpty() || passwordString.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please enter in your email and password to login",Toast.LENGTH_LONG).show();
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        keyId = firebaseAuth.getUid();
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class).putExtra(
                                                "USER_ID",keyId);
                                        intent.putExtra("EMAIL",emailString);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this,"Failed to login",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );
                }
            }
        });
    }
}