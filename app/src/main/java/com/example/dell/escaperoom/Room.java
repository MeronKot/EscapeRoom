package com.example.dell.escaperoom;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dell.escaperoom.Logic.GameTimer;

import java.math.RoundingMode;
import java.util.Timer;
import java.util.TimerTask;

public class Room extends AppCompatActivity {

    private static final int NUM_OF_CHALLENGES = 5;
    private ImageButton temp;
    private ImageButton lamp;
    private ImageButton puzzle;
    private ImageButton simon;
    private ImageButton findDiff;
    private boolean [] challenges;
    public static boolean onGame = false;
    private boolean destroy = false;
    private String theTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        challenges = new boolean[NUM_OF_CHALLENGES];

        temp = (ImageButton)findViewById(R.id.topRightPic);
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,ChemistryChallenge.class);
                startActivityForResult(intent,0);
            }
        });

        lamp = (ImageButton)findViewById(R.id.lamp);
        lamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,LampChallenge.class);
                startActivityForResult(intent,1);
            }
        });

        puzzle = (ImageButton) findViewById(R.id.puzzleBtn);
        puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,PuzzleActivity.class);
                startActivityForResult(intent,2);
            }
        });

        simon = (ImageButton) findViewById(R.id.simonBtn);
        simon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,SimonSaysActivity.class);
                startActivityForResult(intent,3);
            }
        });

        findDiff = (ImageButton)findViewById(R.id.findDiffPic);
        findDiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,FindDiffPicActivity.class);
                startActivityForResult(intent,4);
            }
        });

        startTicking();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    challenges[0] = data.getBooleanExtra("tempChallenge",false);
                    if(challenges[0])
                        temp.setVisibility(View.INVISIBLE);
                    checkIfAllChallengesAreDone();
                    break;
                case 1:
                    challenges[1] = data.getBooleanExtra("lampChallenge",false);
                    if(challenges[1])
                        lamp.setVisibility(View.INVISIBLE);
                    checkIfAllChallengesAreDone();
                    break;
                case 2:
                    challenges[2] = data.getBooleanExtra("puzzleChallenge",false);
                    if(challenges[2])
                        puzzle.setVisibility(View.INVISIBLE);
                    checkIfAllChallengesAreDone();
                    break;
                case 3:
                    challenges[3] = data.getBooleanExtra("simonChallenge",false);
                    if(challenges[3])
                        simon.setVisibility(View.INVISIBLE);
                    checkIfAllChallengesAreDone();
                    break;
                case 4:
                    challenges[4] = data.getBooleanExtra("findDiffPic",false);
                    if(challenges[4])
                        findDiff.setVisibility(View.INVISIBLE);
                    checkIfAllChallengesAreDone();
                default:
                    break;
            }
        }
    }

    private void checkIfAllChallengesAreDone(){
        int i = 0;
        for(boolean challenge: challenges)
            if(challenge)
                i++;
        if(i == NUM_OF_CHALLENGES){
            Toast.makeText(this,"Congratulations, you ran away from this room, see you!",Toast.LENGTH_LONG).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            },1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        onGame = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        onGame = false;
    }

    public void startTicking() {
        /*Thread t = */new Thread(new Runnable() {
            @Override
            public void run() {
                while (!destroy) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(onGame) {
                        GameTimer.getInstance().tick();
                        //Log.d("Timer: ", GameTimer.getInstance().toString());
                        theTime = GameTimer.getInstance().toString();
                    }
                        /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //timerText.setText(timer.toString());
                            Log.d("Timer: ", GameTimer.getInstance().toString());
                        }
                    });*/
                }
            }
        }).start();
        //t.start();
    }
}
