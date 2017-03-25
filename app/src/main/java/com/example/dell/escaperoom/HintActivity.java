package com.example.dell.escaperoom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.escaperoom.Logic.Question;
//import com.example.dell.escaperoom.Logic.QuestionsHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HintActivity extends AppCompatActivity {

    private TextView quest;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private int theRightAnswer;

    private static int numOfQuest = 0;

    private DatabaseReference databaseQuestions;

    private List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        databaseQuestions = FirebaseDatabase.getInstance().getReference("Questions");
        questionList = new ArrayList<Question>();

        //Question q = QuestionsHandler.getInstance().getQuestion();
        quest = (TextView) findViewById(R.id.theQuest);
        answer1 = (Button) findViewById(R.id.ans1);
        answer2 = (Button) findViewById(R.id.ans2);
        answer3 = (Button) findViewById(R.id.ans3);
        answer4 = (Button) findViewById(R.id.ans4);
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == theRightAnswer)
                {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Wrong answer",Toast.LENGTH_SHORT).show();
                }
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (2 == theRightAnswer) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Wrong answer",Toast.LENGTH_SHORT).show();
                }
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (3 == theRightAnswer)
                {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Wrong answer",Toast.LENGTH_SHORT).show();
                }
            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (4 == theRightAnswer)
                {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Wrong answer",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseQuestions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionList.clear();
                numOfQuest = 0;
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    Question quest = questionSnapshot.getValue(Question.class);
                    questionList.add(quest);
                    numOfQuest++;
                }

                Question q = getQuestion();
                quest.setText(q.getQuestion());
                answer1.setText(q.getA1());
                answer2.setText(q.getA2());
                answer3.setText(q.getA3());
                answer4.setText(q.getA4());
                theRightAnswer = q.getAnswer();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public Question getQuestion(){
        Random random = new Random();
        int qIdx = random.nextInt(numOfQuest);
        return questionList.get(qIdx);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Room.onGame = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Room.onGame = false;
    }
}
