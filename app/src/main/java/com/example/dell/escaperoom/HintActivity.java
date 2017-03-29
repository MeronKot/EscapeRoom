package com.example.dell.escaperoom;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.escaperoom.Database.PlayerHandler;
import com.example.dell.escaperoom.Database.DBObjects.Question;
//import com.example.dell.escaperoom.Logic.QuestionsHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
    private List<Integer> keys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        databaseQuestions = FirebaseDatabase.getInstance().getReference("Questions");
        questionList = new ArrayList<Question>();
        keys = new ArrayList<Integer>();

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
                keys.clear();
                numOfQuest = 0;
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    Question quest = questionSnapshot.getValue(Question.class);
                    questionList.add(quest);
                    keys.add(Integer.parseInt(questionSnapshot.getKey()));
                    numOfQuest++;
                }

                Question q = getQuestion();
                if(q != null) {
                    quest.setText(q.getQuestion());
                    answer1.setText(q.getA1());
                    answer2.setText(q.getA2());
                    answer3.setText(q.getA3());
                    answer4.setText(q.getA4());
                    theRightAnswer = q.getAnswer();
                }
                else{
                    quest.setText("no hints for you, wait for update");
                    answer1.setText("");
                    answer2.setText("");
                    answer3.setText("");
                    answer4.setText("");
                    //Toast.makeText(this, "Connection Failed!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public Question getQuestion(){
        int qIdx = PlayerHandler.getInstance().getPlayer().getHints();
        Question q;
        int index;
        if(questionList.size() > qIdx) {
            PlayerHandler.getInstance().getPlayer().setHints(qIdx + 1);
            index = keys.indexOf(qIdx + 1);
            return questionList.get(index);
        }
        else
            return null;
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
