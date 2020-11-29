package com.pbaileyapps.android.mathmania;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private int a,b,c,d;
    private int x,y,z;
    private Random random;
    private TextView questionView, questionNumView, enterAnswerView;
    private RadioGroup group;
    private RadioButton one,two;
    private String question;
    private int total, currentScore;
    private int questionNum;
    private FirebaseDatabase firebaseDatabase;
    private String email;
    private int highScore;
    private int highScoreSubtract;
    private int highScoreMultiply;
    private int highScoreDivide;
    private ArrayList<Integer> linkedList;
    private double num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        final String user_id = getIntent().getStringExtra("USER_ID");
        questionView = findViewById(R.id.question_view);
        group = findViewById(R.id.radio_group);
        one = findViewById(R.id.option_one);
        two = findViewById(R.id.option_two);
        random = new Random();
        total = 0;
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
        DatabaseReference referenceSubtract = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Subtraction");
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
        DatabaseReference referenceDivide = firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Division");
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
            questionNum = 0;

            if (key.equals("Add")) {
                currentScore = 0;
                a = random.nextInt(10) + 1;
                b = random.nextInt(10) + 1;
                c = a + b;
                question = String.valueOf(a) + "+" + String.valueOf(b);
                questionView.setText(question);
                d = random.nextInt(2) + 1;
                //Assign values to radio buttons
                if(d == 1){
                    one.setText(String.valueOf(c));
                    two.setText(String.valueOf(random.nextInt(10)+1));

                }
                else{
                    one.setText(String.valueOf(random.nextInt(10)+1));
                    two.setText(String.valueOf(c));
                }

                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup buttonView, int checkedId) {
                        //if first answer is correct answer
                        if (d == 1) {
                            //if you chose first answer(correct)
                            if (buttonView.getCheckedRadioButtonId() == one.getId()) {
                                //add point and update for next question
                                currentScore++;
                                a = random.nextInt(10) + 1;
                                b = random.nextInt(10) + 1;
                                d = random.nextInt(2) + 1;
                                group.clearCheck();
                                c = a + b;
                                if (d == 1){
                                    one.setText(String.valueOf(c));
                                    two.setText(String.valueOf(random.nextInt(10)));
                                }
                                else{
                                    one.setText(String.valueOf(random.nextInt(10)));
                                    two.setText(String.valueOf(c));
                                }

                                question = a + "+" + b;
                                questionView.setText(question);
                            }
                            //Game Over
                            else {
                                questionView.setText("Game Over");
                                    if(currentScore > highScore) {
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("SCORE", String.valueOf(currentScore));
                                        firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Addition").setValue(currentScore);
                                    }

                                }
                            }
                        //if second button is the correct one
                        else{
                            //if you chose the second button(correct)
                            if (buttonView.getCheckedRadioButtonId() == two.getId()) {
                                //update next question
                                currentScore++;
                                a = random.nextInt(10) + 1;
                                b = random.nextInt(10) + 1;
                                c = a + b;
                                d = random.nextInt(2) + 1;
                                group.clearCheck();
                                if (d == 1){
                                    one.setText(String.valueOf(c));
                                    two.setText(String.valueOf(random.nextInt(10)));
                                }
                                else{
                                    one.setText(String.valueOf(random.nextInt(10)));
                                    two.setText(String.valueOf(c));
                                }
                                question = a + "+" + b;
                                questionView.setText(question);
                            } else {
                                questionView.setText("Game Over");
                                    if(currentScore > highScore) {
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("SCORE", String.valueOf(currentScore));
                                        firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Addition").setValue(currentScore);
                                    }

                                }

                        }
                    }
                });
            }
            else if (key.equals("Subtract")) {
                currentScore = 0;
                a = random.nextInt(10) + 1;
                b = random.nextInt(10) + 1;
                c = a - b;
                question = String.valueOf(a) + "-" + String.valueOf(b);
                questionView.setText(question);
                d = random.nextInt(2) + 1;
                //Assign values to radio buttons
                if(d == 1){
                    one.setText(String.valueOf(c));
                    two.setText(String.valueOf(random.nextInt(10)+1));

                }
                else{
                    one.setText(String.valueOf(random.nextInt(10)+1));
                    two.setText(String.valueOf(c));
                }

                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup buttonView, int checkedId) {
                        //if first answer is correct answer
                        if (d == 1) {
                            //if you chose first answer(correct)
                            if (buttonView.getCheckedRadioButtonId() == one.getId()) {
                                //add point and update for next question
                                currentScore++;
                                a = random.nextInt(10) + 1;
                                b = random.nextInt(10) + 1;
                                d = random.nextInt(2) + 1;
                                group.clearCheck();
                                c = a - b;
                                if (d == 1){
                                    one.setText(String.valueOf(c));
                                    two.setText(String.valueOf(random.nextInt(10)));
                                }
                                else{
                                    one.setText(String.valueOf(random.nextInt(10)));
                                    two.setText(String.valueOf(c));
                                }

                                question = a + "-" + b;
                                questionView.setText(question);
                            }
                            //Game Over
                            else {
                                questionView.setText("Game Over");
                                if(currentScore > highScoreSubtract) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("SCORE", String.valueOf(currentScore));
                                    firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Subtraction").setValue(currentScore);
                                }

                            }
                        }
                        //if second button is the correct one
                        else{
                            //if you chose the second button(correct)
                            if (buttonView.getCheckedRadioButtonId() == two.getId()) {
                                //update next question
                                currentScore++;
                                a = random.nextInt(10) + 1;
                                b = random.nextInt(10) + 1;
                                c = a - b;
                                d = random.nextInt(2) + 1;
                                group.clearCheck();
                                if (d == 1){
                                    one.setText(String.valueOf(c));
                                    two.setText(String.valueOf(random.nextInt(10)));
                                }
                                else{
                                    one.setText(String.valueOf(random.nextInt(10)));
                                    two.setText(String.valueOf(c));
                                }
                                question = a + "-" + b;
                                questionView.setText(question);
                            } else {
                                questionView.setText("Game Over");
                                if(currentScore > highScoreSubtract) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("SCORE", String.valueOf(currentScore));
                                    firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Subtraction").setValue(currentScore);
                                }

                            }

                        }
                    }
                });

            }
            else if(key.equals("Multiply")){
                currentScore = 0;
                a = random.nextInt(10) + 1;
                b = random.nextInt(10) + 1;
                c = a * b;
                question = String.valueOf(a) + "x" + String.valueOf(b);
                questionView.setText(question);
                d = random.nextInt(2) + 1;
                //Assign values to radio buttons
                if(d == 1){
                    one.setText(String.valueOf(c));
                    two.setText(String.valueOf(random.nextInt(10)+1));

                }
                else{
                    one.setText(String.valueOf(random.nextInt(10)+1));
                    two.setText(String.valueOf(c));
                }

                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup buttonView, int checkedId) {
                        //if first answer is correct answer
                        if (d == 1) {
                            //if you chose first answer(correct)
                            if (buttonView.getCheckedRadioButtonId() == one.getId()) {
                                //add point and update for next question
                                currentScore++;
                                a = random.nextInt(10) + 1;
                                b = random.nextInt(10) + 1;
                                d = random.nextInt(2) + 1;
                                group.clearCheck();
                                c = a * b;
                                if (d == 1){
                                    one.setText(String.valueOf(c));
                                    two.setText(String.valueOf(random.nextInt(10)));
                                }
                                else{
                                    one.setText(String.valueOf(random.nextInt(10)));
                                    two.setText(String.valueOf(c));
                                }

                                question = a + "x" + b;
                                questionView.setText(question);
                            }
                            //Game Over
                            else {
                                questionView.setText("Game Over");
                                if(currentScore > highScore) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("SCORE", String.valueOf(currentScore));
                                    firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Multiplication").setValue(currentScore);
                                }

                            }
                        }
                        //if second button is the correct one
                        else{
                            //if you chose the second button(correct)
                            if (buttonView.getCheckedRadioButtonId() == two.getId()) {
                                //update next question
                                currentScore++;
                                a = random.nextInt(10) + 1;
                                b = random.nextInt(10) + 1;
                                c = a * b;
                                d = random.nextInt(2) + 1;
                                group.clearCheck();
                                if (d == 1){
                                    one.setText(String.valueOf(c));
                                    two.setText(String.valueOf(random.nextInt(10)));
                                }
                                else{
                                    one.setText(String.valueOf(random.nextInt(10)));
                                    two.setText(String.valueOf(c));
                                }
                                question = a + "x" + b;
                                questionView.setText(question);
                            } else {
                                questionView.setText("Game Over");
                                if(currentScore > highScore) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("SCORE", String.valueOf(currentScore));
                                    firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Multiplication").setValue(currentScore);
                                }

                            }

                        }
                    }
                });
            }
            else if (key.equals("Divide")) {

                final DecimalFormat format = new DecimalFormat();
                format.applyPattern("###");
                linkedList = division();
                currentScore = 0;
                a = linkedList.get(0);
                b = linkedList.get(1);
                c = a / b;
                question = String.valueOf(a) + "/" + String.valueOf(b);
                questionView.setText(question);
                d = random.nextInt(2) + 1;
                //Assign values to radio buttons
                if(d == 1){
                    one.setText(String.valueOf(c));
                    two.setText(String.valueOf(random.nextInt(10)+1));

                }
                else{
                    one.setText(String.valueOf(random.nextInt(10)+1));
                    two.setText(String.valueOf(c));
                }

                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup buttonView, int checkedId) {
                        //if first answer is correct answer
                        if (d == 1) {
                            //if you chose first answer(correct)
                            if (buttonView.getCheckedRadioButtonId() == one.getId()) {
                                //add point and update for next question
                                currentScore++;
                                linkedList = division();
                                a = linkedList.get(0);
                                b = linkedList.get(1);
                                d = random.nextInt(2) + 1;
                                group.clearCheck();
                                c = a / b;
                                if (d == 1){
                                    one.setText(String.valueOf(c));
                                    two.setText(String.valueOf(random.nextInt(10)));
                                }
                                else{
                                    one.setText(String.valueOf(random.nextInt(10)));
                                    two.setText(String.valueOf(c));
                                }

                                question = a + "/" + b;
                                questionView.setText(question);
                            }
                            //Game Over
                            else {
                                questionView.setText("Game Over");
                                if(currentScore > highScoreDivide) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("SCORE", String.valueOf(currentScore));
                                    firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Division").setValue(currentScore);
                                }

                            }
                        }
                        //if second button is the correct one
                        else{
                            //if you chose the second button(correct)
                            if (buttonView.getCheckedRadioButtonId() == two.getId()) {
                                //update next question
                                currentScore++;
                                linkedList = division();
                                a = linkedList.get(0);
                                b = linkedList.get(1);
                                c = a / b;
                                d = random.nextInt(2) + 1;
                                group.clearCheck();
                                if (d == 1){
                                    one.setText(String.valueOf(c));
                                    two.setText(String.valueOf(random.nextInt(10)));
                                }
                                else{
                                    one.setText(String.valueOf(random.nextInt(10)));
                                    two.setText(String.valueOf(c));
                                }
                                question = a + "/" + b;
                                questionView.setText(question);
                            } else {
                                questionView.setText("Game Over");
                                if(currentScore > highScoreDivide) {
                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("SCORE", String.valueOf(currentScore));
                                    firebaseDatabase.getReference().child(user_id).child("Scores").child("High Scores").child("Division").setValue(currentScore);
                                }

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