package com.pbaileyapps.android.mathmania;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private TextView signout;
    private TextView add,subtract,multiply,divide;
    private String addString, subtractString, multiplyString, divideString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        String user_id = getIntent().getStringExtra("USER_ID");
        String email = getIntent().getStringExtra("EMAIL");
        Toolbar toolbar = findViewById(R.id.idtoolbar);
        TextView emailView = findViewById(R.id.toolbarEmail);
        emailView.setText(email);
        setSupportActionBar(toolbar);
        add = findViewById(R.id.addHighScore);
        subtract = findViewById(R.id.subtractHighScore);
        multiply = findViewById(R.id.multiplyHighScore);
        divide = findViewById(R.id.divideHighScore);
        firebaseAuth = FirebaseAuth.getInstance();
        signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(StatsActivity.this,LoginActivity.class));
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referenceAdd = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Addition");
        DatabaseReference referenceSubtract = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Subtract");
        DatabaseReference referenceMultiply = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Multiply");
        DatabaseReference referenceDivide = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Divide");

        referenceAdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    addString = "Addition: \n" +snapshot.getValue().toString();
                    add.setText(addString);
                }
                else {
                    addString = "No high score yet";
                    add.setText(addString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.getMessage();
            }
        });
        referenceSubtract.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    subtractString = "Subtraction: \n" +snapshot.getValue().toString();
                    subtract.setText(subtractString);
                }
                else {
                    subtractString = "No high score yet";
                    subtract.setText(subtractString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        referenceMultiply.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    multiplyString = "Multiplication: \n" +snapshot.getValue().toString();
                    multiply.setText(multiplyString);
                }
                else {
                    multiplyString = "No high score yet";
                    multiply.setText(multiplyString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        referenceDivide.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    divideString = "Division: \n" +snapshot.getValue().toString();
                    divide.setText(divideString);
                }
                else {
                    divideString = "No high score yet";
                    divide.setText(divideString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}