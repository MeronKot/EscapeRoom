package com.example.dell.escaperoom;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.escaperoom.Logic.SimonSaysLogic;

import static com.example.dell.escaperoom.R.drawable.ic_purple_button;

public class SimonSaysActivity extends AppCompatActivity {

    private static final int NUM_OF_HINTS = 2;
    private String [] hints = {"Try to remember the steps.",
            "There is 5 steps."};
    private int hintCounter = 0;
    private Button hintButton;
    private Button hide;
    private RelativeLayout hintContainer;
    private ImageButton blue;
    private ImageButton green;
    private ImageButton red;
    private ImageButton yellow;

    private MediaPlayer a;
    private MediaPlayer b;
    private MediaPlayer c;
    private MediaPlayer d;

    private SimonSaysLogic logic;
    private boolean inChallenge=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_says);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        logic = new SimonSaysLogic();

        blue = (ImageButton) findViewById(R.id.blue);
        green = (ImageButton) findViewById(R.id.green);
        red = (ImageButton) findViewById(R.id.red);
        yellow = (ImageButton) findViewById(R.id.yellow);

        a = MediaPlayer.create(this, R.raw.a_sharp);
        b = MediaPlayer.create(this, R.raw.c_sharp);
        c = MediaPlayer.create(this, R.raw.d_sharp);
        d = MediaPlayer.create(this, R.raw.f_sharp);

        hintButton = (Button)findViewById(R.id.hint);
        hintContainer = (RelativeLayout)findViewById(R.id.hintContainer);
        hide = (Button)findViewById(R.id.hideInHint);

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hintCounter == NUM_OF_HINTS){
                    Toast.makeText(getApplicationContext(),"You don't have hints any more",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SimonSaysActivity.this,HintActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        play(logic.getNewComputerMoves());
        Room.onGame = true;
        inChallenge = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        Room.onGame = false;
        inChallenge = false;
    }

    private void play(final int[] moves) {
        Log.d("Simon: ", moves.toString());
        new CountDownTimer(1000*(logic.NUM_OF_MOVES)+500, 1000){
            int i=0;
            View lastBtn = null;
            int lastBackground = 0;

            @Override
            public void onTick(long millisUntilFinished) {

                if(i<=logic.NUM_OF_MOVES && inChallenge){

                    switch(moves[i]){
                        case SimonSaysLogic.BLUE:
                            doMove(blue, R.drawable.ic_blue_button);

                            break;

                        case SimonSaysLogic.GREEN:
                            doMove(green, R.drawable.ic_green_button);

                            break;

                        case SimonSaysLogic.RED:
                            doMove(red, R.drawable.ic_red_button);

                            break;

                        case SimonSaysLogic.YELLOW:
                            doMove(yellow, R.drawable.ic_yellow_button);

                            break;
                    }
                }
                i++;
            }

            @Override
            public void onFinish() {
                blue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v, SimonSaysLogic.BLUE, R.drawable.ic_blue_button);
                    }
                });

                green.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v, SimonSaysLogic.GREEN, R.drawable.ic_green_button);
                    }
                });

                red.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v, SimonSaysLogic.RED, R.drawable.ic_red_button);
                    }
                });

                yellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v, SimonSaysLogic.YELLOW, R.drawable.ic_yellow_button);
                    }
                });
            }
        }.start();


    }

    private void doMove(final View btn, final int background) {
        btn.setBackgroundResource(R.drawable.ic_purple_button);
        switch (background){
            case R.drawable.ic_blue_button:
                a.start();
                break;
            case R.drawable.ic_green_button:
                b.start();
                break;
            case R.drawable.ic_red_button:
                c.start();
                break;
            case R.drawable.ic_yellow_button:
                d.start();
                break;
        }
        new CountDownTimer(500,500){
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                btn.setBackgroundResource(background);
            }
        }.start();
    }

    private void click(final View button, int color, final int background) {

        boolean move = logic.buttonPressed(color);
        if(move){
            doMove(button, background);
            if(logic.getMove() == SimonSaysLogic.NUM_OF_MOVES){
                //TODO: youWin!!
                //Log.d("Simon: ", "win win win win win win");
                Intent intent = new Intent();
                intent.putExtra("simonChallenge",true);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        else{
            //TODO: fail!!!
            button.setBackgroundResource(R.drawable.ic_fail);
            new CountDownTimer(501,500){

                @Override
                public void onTick(long millisUntilFinished) {
                    //recreate();
                }

                @Override
                public void onFinish() {
                    recreate();
                }
            }.start();
        }
    }

}
