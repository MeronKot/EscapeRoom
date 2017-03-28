package com.example.dell.escaperoom;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstructionActivity extends AppCompatActivity {

    private TextView welcome;
    private TextView text;
    private Button goodluck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        welcome = (TextView)findViewById(R.id.title);
        text = (TextView)findViewById(R.id.instruText);

        String str = "In this room you need to reveal hidden objects. " +
                "Each object will open for you a challenge that you have to solve." +
                "If you have difficulties during the challenge press ? to get hints. " +
                "In order to see the hint you have to answer correctly multiple choice question. " +
                "After finishing all the challenges in the room you will escape from this room. ";

        welcome.setText("Welcome!");
        text.setText(str);

        goodluck = (Button)findViewById(R.id.goodluck);
        goodluck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
