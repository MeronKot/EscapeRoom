package com.example.dell.escaperoom;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dell.escaperoom.Database.DBObjects.Record;
import com.example.dell.escaperoom.Database.PlayerHandler;
import com.example.dell.escaperoom.Logic.GameTimer;
import com.example.dell.escaperoom.Database.DBObjects.Player;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class RoomActivity extends AppCompatActivity {

    private ImageButton temp;
    private ImageButton lamp;
    private ImageButton puzzle;
    private ImageButton simon;
    private ImageButton findDiff;

    private boolean challengeResult;
    public static boolean onGame = false;
    private boolean destroy = false;
    private String theTime = "";
    private DatabaseReference databaseRecords;

    private MediaPlayer clockSound;
    private boolean ticking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //challenges = new boolean[NUM_OF_CHALLENGES];
        Player p = PlayerHandler.getInstance().getPlayer();
        //while((p=PlayerHandler.getInstance().getPlayer()) == null);
        databaseRecords = FirebaseDatabase.getInstance().getReference("Records");

        if(p != null && p.isWinner()){
            Intent intent = new Intent(RoomActivity.this, WinActivity.class);
            //intent.putExtra("name",p.getName());
            //intent.putExtra("time",p.getTime());
            startActivity(intent);
            finish();
        }
        else {
            if (p.getLevel1() == 0) {
                temp = (ImageButton) findViewById(R.id.window);
                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RoomActivity.this, ChemistryActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });
            }

            if (p.getLevel2() == 0) {
                lamp = (ImageButton) findViewById(R.id.lamp);
                lamp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RoomActivity.this, LampActivity.class);
                        startActivityForResult(intent, 1);
                    }
                });
            }

            if (p.getLevel3() == 0) {
                puzzle = (ImageButton) findViewById(R.id.puzzleBtn);
                puzzle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RoomActivity.this, PuzzleActivity.class);
                        startActivityForResult(intent, 2);
                    }
                });
            }

            if (p.getLevel4() == 0) {
                simon = (ImageButton) findViewById(R.id.simonBtn);
                simon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RoomActivity.this, SimonSaysActivity.class);
                        startActivityForResult(intent, 3);
                    }
                });
            }

            if (p.getLevel5() == 0) {
                findDiff = (ImageButton) findViewById(R.id.findDiffPic);
                findDiff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RoomActivity.this, FindDiffPicActivity.class);
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
                    Player p = PlayerHandler.getInstance().getPlayer();

                    Record record = new Record();
                    record.setName(p.getName());
                    record.setScore(p.getTime());
                    databaseRecords.child(p.getId()).setValue(record);
                    Intent intent = new Intent(RoomActivity.this, WinActivity.class);
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                ticking = true;
                clockSound = MediaPlayer.create(RoomActivity.this, R.raw.clock_sound);
                while (ticking){
                   if(!clockSound.isPlaying())
                   {

                       clockSound.start();

                   }
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onGame = false;
        ticking = false;

        if(clockSound != null) {
            clockSound.stop();
        }
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
                        PlayerHandler.getInstance().getPlayer().setTime(theTime.toString());
                    }
                }
            }
        }).start();
    }
}
