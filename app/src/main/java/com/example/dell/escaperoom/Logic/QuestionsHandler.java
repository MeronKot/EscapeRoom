/*
package com.example.dell.escaperoom.Logic;

import android.widget.ArrayAdapter;


import com.example.dell.escaperoom.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
*/

/**
 * Created by yaelgersh on 21/03/2017.
 */

/*public class QuestionsHandler {
    private static int numOfQuest = 0;
    private DatabaseReference databaseQuestions;

    private List<Question> questionList;

    public static QuestionsHandler instance = new QuestionsHandler();

    private QuestionsHandler(){
        //TODO: numOfQuest  =  (from DB) how many questions the player already got

        databaseQuestions = FirebaseDatabase.getInstance().getReference("Questions");
        questionList = new ArrayList<Question>();
    }

    public void startListening(){
        databaseQuestions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                questionList.clear();

                for(DataSnapshot  questionSnapshot : dataSnapshot.getChildren()){
                    Question quest = questionSnapshot.getValue(Question.class);
                    questionList.add(quest);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static QuestionsHandler getInstance(){
        return instance;
    }

    public Question getQuestion(){
        return questionList.get(numOfQuest);
    }
}*/
