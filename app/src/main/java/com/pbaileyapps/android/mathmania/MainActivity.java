package com.pbaileyapps.android.mathmania;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        private ArrayList<String> arrayList, signList;
        private FirebaseAuth auth;
        private AlarmManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.idtoolbar);
        setSupportActionBar(toolbar);
        final String user_id = getIntent().getStringExtra("USER_ID");
        final String email = getIntent().getStringExtra("EMAIL");
        TextView emailView = findViewById(R.id.toolbarEmail);
        emailView.setText(email);
        TextView signOut = findViewById(R.id.signout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        ImageView account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StatsActivity.class);
                intent.putExtra("USER_ID",user_id);
                intent.putExtra("EMAIL",email);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MathAdapter adapter = new MathAdapter(this);
        arrayList= new ArrayList<>();
        arrayList.add("Addition");
        arrayList.add("Subtraction");
        arrayList.add("Multiplication");
        arrayList.add("Division");
        signList = new ArrayList<>();
        signList.add("+");
        signList.add("-");
        signList.add("x");
        signList.add("/");
        adapter.setSubjectList(arrayList);
        adapter.setSignList(signList);
        adapter.setSelectorInstance(new MathAdapter.subjectSelector() {
            Intent i = new Intent(MainActivity.this,QuizActivity.class);
            @Override
            public void selectSubject(int position) {
                String string = arrayList.get(position);
                switch(string){
                    case("Addition"):
                        i.putExtra("QUIZ","Add");
                        i.putExtra("USER_ID", user_id);
                        i.putExtra("EMAIL",email);
                        startActivity(i);
                        break;
                    case("Subtraction"):
                        i.putExtra("QUIZ","Subtract");
                        i.putExtra("USER_ID", user_id);
                        i.putExtra("EMAIL",email);
                        startActivity(i);
                        break;
                    case("Multiplication"):
                        i.putExtra("QUIZ", "Multiply");
                        i.putExtra("USER_ID", user_id);
                        i.putExtra("EMAIL",email);
                        startActivity(i);
                        break;
                    case("Division"):
                        i.putExtra("QUIZ","Divide");
                        i.putExtra("USER_ID", user_id);
                        i.putExtra("EMAIL",email);
                        startActivity(i);
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}