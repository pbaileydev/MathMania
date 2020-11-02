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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        String user_id = getIntent().getStringExtra("USER_ID");
        String email = getIntent().getStringExtra("EMAIL");
        Toolbar toolbar = findViewById(R.id.idtoolbar);
        TextView emailView = findViewById(R.id.toolbarEmail);
        emailView.setText(email.substring(0,10));
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
                    add.setText(snapshot.getValue().toString());
                }
                else {
                    add.setText("No high score yet");
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
                    subtract.setText(snapshot.getValue().toString());
                }
                else {
                    subtract.setText("No high score yet");
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
                    multiply.setText(snapshot.getValue().toString());
                }
                else {
                    multiply.setText("No high score yet");
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
                    divide.setText(snapshot.getValue().toString());
                }
                else {
                    divide.setText("No high score yet");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}