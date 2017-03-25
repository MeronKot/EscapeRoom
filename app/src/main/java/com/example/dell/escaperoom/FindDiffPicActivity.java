package com.example.dell.escaperoom;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class FindDiffPicActivity extends AppCompatActivity {

    private static final int NUM_OF_DIFFERENCES = 6;
    private ImageButton [] differences;
    private boolean [] solveDiff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_diff_pic);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        solveDiff = new boolean[NUM_OF_DIFFERENCES];
        for(int i = 0 ; i < NUM_OF_DIFFERENCES; i++)
            solveDiff[i] = false;

        differences = new ImageButton[NUM_OF_DIFFERENCES];
        differences[0] = (ImageButton)findViewById(R.id.topLeftTree);
        differences[1] = (ImageButton)findViewById(R.id.cloud);
        differences[2] = (ImageButton)findViewById(R.id.earring);
        differences[3] = (ImageButton)findViewById(R.id.handFan);
        differences[4] = (ImageButton)findViewById(R.id.armPaint);
        differences[5] = (ImageButton)findViewById(R.id.stone);
        setListeners();
    }

    private void setListeners() {
        for(int i = 0 ; i < NUM_OF_DIFFERENCES ; i++)
        {
            differences[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findDiff(v);
                }
            });
        }
    }

    private void findDiff(View v) {
        switch (v.getId()){
            case R.id.topLeftTree:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.v);
                solveDiff[0] = true;
                checkIfDone();
                break;
            case R.id.cloud:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.v);
                solveDiff[1] = true;
                checkIfDone();
                break;
            case R.id.earring:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.v);
                solveDiff[2] = true;
                checkIfDone();
                break;
            case R.id.handFan:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.v);
                solveDiff[3] = true;
                checkIfDone();
                break;
            case R.id.armPaint:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.v);
                solveDiff[4] = true;
                checkIfDone();
                break;
            case R.id.stone:
                v.setClickable(false);
                v.setBackgroundResource(R.drawable.v);
                solveDiff[5] = true;
                checkIfDone();
                break;
            default:
                break;
        }
    }

    private void checkIfDone() {
        int i = 0;
        for(boolean diff: solveDiff)
            if(diff)
                i++;
        if(i == NUM_OF_DIFFERENCES){
            Toast.makeText(this,"Good, analyze pictures is your expertise",Toast.LENGTH_LONG).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra("findDiffPic",true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            },1000);
        }
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
