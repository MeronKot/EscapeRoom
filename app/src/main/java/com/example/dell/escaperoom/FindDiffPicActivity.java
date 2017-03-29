package com.example.dell.escaperoom;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class FindDiffPicActivity extends AppCompatActivity {

    private String [] hints = {"Clouds..., top left tree...",
            "Earring..., her fan...",
            "Look at her arms and stones...",
            "Window, grass...",
            "Below her fan, her dress..."};
    private static final int NUM_OF_DIFFERENCES = 10,NUM_OF_HINTS = 5;
    private int hintCounter = 0;
    private Button hintButton;
    private Button hide;
    private RelativeLayout hintContainer;
    private ImageButton [] differences;
    private boolean [] solveDiff;

    private MediaPlayer tap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_diff_pic);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tap = MediaPlayer.create(this.getApplicationContext(), R.raw.tap);

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
        differences[6] = (ImageButton)findViewById(R.id.window);
        differences[7] = (ImageButton)findViewById(R.id.underFan);
        differences[8] = (ImageButton)findViewById(R.id.dress);
        differences[9] = (ImageButton)findViewById(R.id.grass);
        hintButton = (Button)findViewById(R.id.hint);
        hintContainer = (RelativeLayout)findViewById(R.id.hintContainer);
        hide = (Button)findViewById(R.id.hideInHint);

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

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hintCounter == NUM_OF_HINTS){
                    Toast.makeText(getApplicationContext(),"You don't have hints any more",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(FindDiffPicActivity.this,HintActivity.class);
                startActivityForResult(intent,hintCounter);
            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintContainer.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (hintCounter == NUM_OF_HINTS){
                Toast.makeText(this,"No more hints",Toast.LENGTH_SHORT).show();
                hintButton.setClickable(false);
                return;
            }else{
                TextView ht = (TextView)findViewById(R.id.hintText);
                ht.setText("");
                ht.setText(hints[hintCounter++]);
                hintContainer.setVisibility(View.VISIBLE);
            }

        }
    }

    private void findDiff(View v) {

        switch (v.getId()){
            case R.id.topLeftTree:
                solveDiff[0] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.cloud:
                solveDiff[1] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.earring:
                solveDiff[2] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.handFan:
                solveDiff[3] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.armPaint:
                solveDiff[4] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.stone:
                solveDiff[5] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.window:
                solveDiff[6] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.underFan:
                solveDiff[7] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.dress:
                solveDiff[8] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
                checkIfDone();
                break;
            case R.id.grass:
                solveDiff[9] = true;
                v.setClickable(false);
                tap.start();
                v.setBackgroundResource(R.drawable.ic_fail);
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
