package com.pbaileyapps.android.mathmania;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private TextView add,subtract,multiply,divide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        TextView textView = findViewById(R.id.statsEmail);
        add = findViewById(R.id.addHighScore);
        subtract = findViewById(R.id.subtractHighScore);
        multiply = findViewById(R.id.multiplyHighScore);
        divide = findViewById(R.id.divideHighScore);
        String user_id = getIntent().getStringExtra("USER_ID");
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referenceAdd = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Addition");
        DatabaseReference referenceSubtract = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Subtract");
        DatabaseReference referenceMultiply = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Multiply");
        DatabaseReference referenceDivide = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Divide");

        referenceAdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               add.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                error.getMessage();
            }
        });
        referenceSubtract.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subtract.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        referenceMultiply.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                multiply.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        referenceDivide.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                divide.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String email = getIntent().getStringExtra("EMAIL");
        textView.setText(email);

    }
}