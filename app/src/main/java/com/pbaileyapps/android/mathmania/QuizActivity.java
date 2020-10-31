package com.pbaileyapps.android.mathmania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private LinkedList<Integer> pointList;
    private int a,b,c;
    private double x,y,z;
    private TextView questionView, questionNumView;
    private EditText enterAnswerView;
    private String question;
    private int limit;
    private int questionNum;
    private FirebaseDatabase firebaseDatabase;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final String user_id = getIntent().getStringExtra("USER_ID");
        questionView = findViewById(R.id.question_view);
        questionNumView = findViewById(R.id.question_num);
        enterAnswerView = findViewById(R.id.enter_here);
        Button submitAnswer = findViewById(R.id.submit);
        firebaseDatabase = FirebaseDatabase.getInstance();
        limit = 10;
        pointList = new LinkedList<>();
        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");
        try {
            final String key = intent.getExtras().getString("QUIZ");
            final Random random = new Random();
            questionNum = 0;
            a = random.nextInt(10);
            b = random.nextInt(10);
            if (key.equals("Add")) {
                c = a + b;
                question = "" + a + " + " + b;
                questionView.setText(question);
                questionNumView.setText(String.valueOf(questionNum));
                submitAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int answer = Integer.parseInt(enterAnswerView.getText().toString());
                        if (answer == c) {
                            Integer i = new Integer(1);
                            pointList.add(i);
                        } else {
                            Toast.makeText(QuizActivity.this, "Sorry, that is incorrect", Toast.LENGTH_LONG).show();
                            pointList.add(new Integer(0));
                        }
                        a = random.nextInt(10);
                        b = random.nextInt(10);
                        c = a + b;
                        question = "" + a + " + " + b;
                        questionView.setText(question);
                        enterAnswerView.setText("");
                        questionNum++;
                        questionNumView.setText(String.valueOf(questionNum));
                        if (questionNum == limit) {
                            int total = 0;
                            String response;
                            for (int i = 0; i < pointList.size(); i++) {
                                total += pointList.get(i);
                            }
                            response = "Your score is " + total + "/10";
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("SCORE",String.valueOf(total));
                            firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Addition").setValue(response);

                            questionView.setText(response);
                        }
                    }
                });
            } else if (key.equals("Subtract")) {
                c = a - b;
                question = "" + a + " - " + b;
                questionView.setText(question);
                questionNumView.setText(String.valueOf(questionNum));
                submitAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int answer = Integer.parseInt(enterAnswerView.getText().toString());
                        if (answer == c) {
                            Integer i = new Integer(1);
                            pointList.add(i);
                        } else {
                            Toast.makeText(QuizActivity.this, "Sorry, that is incorrect", Toast.LENGTH_LONG).show();
                            pointList.add(new Integer(0));
                        }
                        a = random.nextInt(10);
                        b = random.nextInt(10);
                        c = a - b;
                        question = "" + a + " - " + b;
                        questionView.setText(question);
                        enterAnswerView.setText("");
                        questionNum++;
                        questionNumView.setText(String.valueOf(questionNum));
                        if (questionNum == limit) {
                            int total = 0;
                            String response;
                            for (int i = 0; i < pointList.size(); i++) {
                                total += pointList.get(i).intValue();
                            }
                            response = "Your score is " + total + "/10";
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("SCORE",String.valueOf(total));
                            firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Subtract").setValue(response);
                            questionView.setText(response);
                        }
                    }
                });
            } else if (key.equals("Multiply")) {
                c = a * b;
                question = "" + a + " x " + b;
                questionView.setText(question);
                questionNumView.setText(String.valueOf(questionNum));
                submitAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int answer = Integer.parseInt(enterAnswerView.getText().toString());
                        if (answer == c) {
                            Integer i = new Integer(1);
                            pointList.add(i);
                        } else {
                            Toast.makeText(QuizActivity.this, "Sorry, that is incorrect", Toast.LENGTH_LONG).show();
                            pointList.add(new Integer(0));
                        }
                        a = random.nextInt(10);
                        b = random.nextInt(10);
                        c = a * b;
                        question = "" + a + " * " + b;
                        questionView.setText(question);
                        enterAnswerView.setText("");
                        questionNum++;
                        questionNumView.setText(String.valueOf(questionNum));
                        if (questionNum == limit) {
                            int total = 0;
                            String response;
                            for (int i = 0; i < pointList.size(); i++) {
                                total += pointList.get(i).intValue();
                            }
                            response = "Your score is " + total + "/10";
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("SCORE",String.valueOf(total));
                            firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Multiply").setValue(response);
                            questionView.setText(response);
                        }
                    }
                });
            } else if (key.equals("Divide")) {
                x = Math.random() * 10 - 1 + 1;
                y = Math.random() * 10 - 1 + 1;
                z = x/y;
                question = "" + x + " / " + y;
                questionView.setText(question);
                questionNumView.setText(String.valueOf(questionNum));
                submitAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double answer = Double.parseDouble(enterAnswerView.getText().toString());
                        if (answer == z) {
                            Integer i = new Integer(1);
                            pointList.add(i);
                        } else {
                            Toast.makeText(QuizActivity.this, "Sorry, that is incorrect", Toast.LENGTH_LONG).show();
                            pointList.add(new Integer(0));
                        }
                        x = Math.random() * 10 - 1 + 1;
                        y =  Math.random() * 10 - 1 + 1;

                        z = a / b;
                        question = "" + x + " / " + y;
                        questionView.setText(question);
                        enterAnswerView.setText("");
                        questionNum++;
                        questionNumView.setText(String.valueOf(questionNum));
                        if (questionNum == limit) {
                            int total = 0;
                            String response;
                            for (int i = 0; i < pointList.size(); i++) {
                                total += pointList.get(i).intValue();
                            }
                            response = "Your score is " + total + "/10";
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("SCORE",String.valueOf(total));
                            hashMap.put("EMAIL",email);
                            firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Divide").setValue(response);
                            questionView.setText(response);
                        }
                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }




    }
}