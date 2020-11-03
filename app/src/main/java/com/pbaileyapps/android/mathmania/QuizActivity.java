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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private LinkedList<Integer> pointList;
    private int a,b,c;
    private int x,y,z;
    private TextView questionView, questionNumView;
    private EditText enterAnswerView;
    private String question;
    private int total;
    private int questionNum;
    private FirebaseDatabase firebaseDatabase;
    private String email;
    private int highScore;
    private int highScoreSubtract;
    private int highScoreMultiply;
    private int highScoreDivide;
    private double num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final String user_id = getIntent().getStringExtra("USER_ID");
        questionView = findViewById(R.id.question_view);
        questionNumView = findViewById(R.id.question_num);
        enterAnswerView = findViewById(R.id.enter_here);
        total = 0;
        Button submitAnswer = findViewById(R.id.submit);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference referenceAdd = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Addition");
        referenceAdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null){
                    highScore = 0;
                }
                else {
                    String string = snapshot.getValue().toString();
                    highScore = Integer.parseInt(string);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference referenceSubtract = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Subtract");
        referenceSubtract.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null){
                    highScoreSubtract = 0;
                }
                else{
                String string = snapshot.getValue().toString();
                highScoreSubtract = Integer.parseInt(string);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference referenceMultiply = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Multiply");
        referenceMultiply.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null){
                    highScoreMultiply = 0;
                }
                else {
                String string = snapshot.getValue().toString();
                highScoreMultiply = Integer.parseInt(string);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference referenceDivide = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Divide");
        referenceDivide.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    highScoreDivide = 0;
                }
                else {
                    String string = snapshot.getValue().toString();
                    highScoreDivide = Integer.parseInt(string);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                            a = random.nextInt(10);
                            b = random.nextInt(10);
                            c = a + b;
                            question = "" + a + " + " + b;
                            questionView.setText(question);
                            enterAnswerView.setText("");
                            questionNum++;
                            questionNumView.setText(String.valueOf(questionNum));
                        }
                        else {
                            questionView.setText("Game Over");
                            for (int i = 0; i < pointList.size(); i++) {
                                total += pointList.get(i);
                            }
                            //Finish
                            if(total > highScore) {
                                String response = String.valueOf(total);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("SCORE", String.valueOf(total));
                                firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Addition").setValue(response);
                            }
                            Toast.makeText(QuizActivity.this, "Sorry, that is incorrect", Toast.LENGTH_LONG).show();

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
                            a = random.nextInt(10);
                            b = random.nextInt(10);
                            c = a - b;
                            question = "" + a + " - " + b;
                            questionView.setText(question);
                            enterAnswerView.setText("");
                            questionNum++;
                            questionNumView.setText(String.valueOf(questionNum));
                        } else {
                            questionView.setText("Game Over");
                            for (int i = 0; i < pointList.size(); i++) {
                                total += pointList.get(i).intValue();
                            }
                            //Finish
                            if (total > highScoreSubtract) {
                                String response = String.valueOf(total);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("SCORE", String.valueOf(total));
                                firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Subtract").setValue(response);
                            }
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
                            a = random.nextInt(10);
                            b = random.nextInt(10);
                            c = a * b;
                            question = "" + a + " x " + b;
                            questionView.setText(question);
                            enterAnswerView.setText("");
                            questionNum++;
                            questionNumView.setText(String.valueOf(questionNum));
                        } else {
                            questionView.setText("Game Over");
                            for (int i = 0; i < pointList.size(); i++) {
                                total += pointList.get(i).intValue();
                            }
                            //Finish
                            if (total > highScoreMultiply) {
                                String response = String.valueOf(total);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("SCORE", String.valueOf(total));
                                firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Multiply").setValue(response);
                            }
                        }


                    }
                });
            } else if (key.equals("Divide")) {

                final DecimalFormat format = new DecimalFormat();
                format.applyPattern("###");
                ArrayList<Integer> linkedList = division();
                x = linkedList.get(0).intValue();
                y = linkedList.get(1).intValue();
                num = x/y;
                question = x  + " / " + y;
                questionView.setText(question);
                questionNumView.setText(String.valueOf(questionNum));
                submitAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int answer = Integer.parseInt(enterAnswerView.getText().toString());
                        if (answer == num) {
                            Integer i = new Integer(1);
                            ArrayList<Integer> nlinkedList = division();
                            x = nlinkedList.get(0);
                            y = nlinkedList.get(1);
                            num = x/y;
                            question = x  + " / " + y;
                            questionView.setText(question);
                            enterAnswerView.setText("");
                            questionNum++;
                            questionNumView.setText(String.valueOf(questionNum));
                            pointList.add(i);
                        } else {
                            questionView.setText("Game Over");
                            for (int i = 0; i < pointList.size(); i++) {
                                total += pointList.get(i).intValue();
                            }
                            //Finish
                            if (total > highScoreDivide) {
                                String response = String.valueOf(total);
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("SCORE", String.valueOf(total));
                                firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Divide").setValue(response);
                            }
                        }


                    }
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }




    }
    public ArrayList<Integer> division() {
        ArrayList<Integer> linkedList = new ArrayList<>();
        boolean flag = false;
        int i = 0;
        while (i == 0) {
            x = new Random().nextInt(10);
            y = new Random().nextInt(10);
            boolean inner = false;
            int p = 0;
            if (y == 0) {
                y = new Random().nextInt(10);
                flag = false;
            } else if (x % y != 0) {
                flag = false;
            } else if (x % y == 0) {
                String question = "x" + " / " + y;
                z = (int) x / y;
                linkedList.add(Integer.valueOf(x));
                linkedList.add(Integer.valueOf(y));
                linkedList.add(Integer.valueOf(z));
                flag = true;
                i++;
            }

        }
        return linkedList;
    }

}