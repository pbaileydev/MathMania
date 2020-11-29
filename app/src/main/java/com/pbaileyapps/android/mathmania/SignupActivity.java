package com.pbaileyapps.android.mathmania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private TextInputEditText firstName,lastName,emailAddress,password;
    private Button register;
    private String fullName, emailString, passwordText;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firstName = findViewById(R.id.textinputEditFirstName);
        lastName = findViewById(R.id.textinputEditLastName);
        emailAddress = findViewById(R.id.textinputEditEmail);
        password = findViewById(R.id.textinputEditPassword);
        register = findViewById(R.id.registerUser);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(firstName.getText().toString()) || TextUtils.isEmpty(lastName.getText().toString())
                        || TextUtils.isEmpty(emailAddress.getText().toString())|| TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(SignupActivity.this,"Please fill in all blanks", Toast.LENGTH_LONG).show();
                }
                else {
                    emailString = emailAddress.getText().toString();
                    passwordText = password.getText().toString();
                    fullName = firstName.getText().toString() + " " + lastName.getText().toString();
                    firebaseAuth.createUserWithEmailAndPassword(emailAddress.getText().toString(), passwordText).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //register user in database
                                        Toast.makeText(SignupActivity.this, "User registered", Toast.LENGTH_LONG).show();
                                        String keyId = firebaseAuth.getUid();

                                        assert keyId != null;
                                        firebaseDatabase.getReference().child(keyId).child("Name").setValue(fullName);
                                        firebaseDatabase.getReference().child(keyId).child("Email").setValue(emailString);

                                    } else {
                                        task.getException().printStackTrace();
                                        Toast.makeText(SignupActivity.this, "User not registered", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );
                }
            }
        });


    }
}