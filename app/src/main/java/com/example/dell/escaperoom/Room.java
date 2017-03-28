package com.example.dell.escaperoom;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dell.escaperoom.Database.PlayerHandler;
import com.example.dell.escaperoom.Database.WinActivity;
import com.example.dell.escaperoom.Logic.GameTimer;
import com.example.dell.escaperoom.Database.DBObjects.Player;

import java.util.Timer;
import java.util.TimerTask;

public class Room extends AppCompatActivity {

    //private static final int NUM_OF_CHALLENGES = 5;
    private ImageButton temp;
    private ImageButton lamp;
    private ImageButton puzzle;
    private ImageButton simon;
    private ImageButton findDiff;
    //private boolean [] challenges;
    private boolean challengeResult;
    public static boolean onGame = false;
    private boolean destroy = false;
    private String theTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //challenges = new boolean[NUM_OF_CHALLENGES];

        Player p = PlayerHandler.getInstance().getPlayer();

        if(p != null && p.isWinner()){
            Intent intent = new Intent(Room.this, WinActivity.class);
            //intent.putExtra("name",p.getName());
            //intent.putExtra("time",p.getTime());
            startActivity(intent);
            finish();
        }
        else {
            if (p.getLevel1() == 0) {
                temp = (ImageButton) findViewById(R.id.topRightPic);
                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Room.this, ChemistryChallenge.class);
                        startActivityForResult(intent, 0);
                    }
                });
            }

            if (p.getLevel2() == 0) {
                lamp = (ImageButton) findViewById(R.id.lamp);
                lamp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Room.this, LampChallenge.class);
                        startActivityForResult(intent, 1);
                    }
                });
            }

            if (p.getLevel3() == 0) {
                puzzle = (ImageButton) findViewById(R.id.puzzleBtn);
                puzzle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Room.this, PuzzleActivity.class);
                        startActivityForResult(intent, 2);
                    }
                });
            }

            if (p.getLevel4() == 0) {
                simon = (ImageButton) findViewById(R.id.simonBtn);
                simon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Room.this, SimonSaysActivity.class);
                        startActivityForResult(intent, 3);
                    }
                });
            }

            if (p.getLevel5() == 0) {
                findDiff = (ImageButton) findViewById(R.id.findDiffPic);
                findDiff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Room.this, FindDiffPicActivity.class);
                        startActivityForResult(intent, 4);
                    }
                });
            }
            onGame = true;
            startTicking();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 0:
                    challengeResult = data.getBooleanExtra("tempChallenge",false);
                    if(challengeResult) {
                        temp.setVisibility(View.INVISIBLE);
                        PlayerHandler.getInstance().getPlayer().setLevel1(1);
                    }
                    checkIfAllChallengesAreDone();
                    break;
                case 1:
                    challengeResult = data.getBooleanExtra("lampChallenge",false);
                    if(challengeResult) {
                        lamp.setVisibility(View.INVISIBLE);
                        PlayerHandler.getInstance().getPlayer().setLevel2(1);
                    }
                    checkIfAllChallengesAreDone();
                    break;
                case 2:
                    challengeResult = data.getBooleanExtra("puzzleChallenge",false);
                    if(challengeResult) {
                        puzzle.setVisibility(View.INVISIBLE);
                        PlayerHandler.getInstance().getPlayer().setLevel3(1);
                    }
                    checkIfAllChallengesAreDone();
                    break;
                case 3:
                    challengeResult = data.getBooleanExtra("simonChallenge",false);
                    if(challengeResult) {
                        simon.setVisibility(View.INVISIBLE);
                        PlayerHandler.getInstance().getPlayer().setLevel4(1);
                    }
                    checkIfAllChallengesAreDone();
                    break;
                case 4:
                    challengeResult = data.getBooleanExtra("findDiffPic",false);
                    if(challengeResult) {
                        findDiff.setVisibility(View.INVISIBLE);
                        PlayerHandler.getInstance().getPlayer().setLevel5(1);
                    }
                    checkIfAllChallengesAreDone();
                default:
                    break;
            }
        }
    }

    private void checkIfAllChallengesAreDone(){
        Player p = PlayerHandler.getInstance().getPlayer();
        if(p.isWinner()){
            Toast.makeText(this,"Congratulations, you ran away from this room, see you!",Toast.LENGTH_LONG).show();
            //TODO: something in database?
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(Room.this, WinActivity.class);
                    startActivity(intent);
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
    protected void onPause() {
        super.onPause();
        onGame = false;
    }

    public void startTicking() {
        if(PlayerHandler.getInstance().getPlayer().getTime() != null){
            GameTimer.getInstance().setTime(PlayerHandler.getInstance().getPlayer().getTime());
        }
        new Thread(new Runnable() {
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
                        runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            PlayerHandler.getInstance().getPlayer().setTime(theTime.toString());
                            Log.d("Timer: ", GameTimer.getInstance().toString());
                        }
                    });
                }
            }
        }).start();
    }
}
