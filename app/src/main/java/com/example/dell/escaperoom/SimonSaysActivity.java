package com.example.dell.escaperoom;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.dell.escaperoom.Logic.SimonSaysLogic;

import static com.example.dell.escaperoom.R.drawable.ic_purple_button;

public class SimonSaysActivity extends AppCompatActivity {

    private ImageButton blue;
    private ImageButton green;
    private ImageButton red;
    private ImageButton yellow;

    private SimonSaysLogic logic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_says);


        logic = new SimonSaysLogic();

        blue = (ImageButton) findViewById(R.id.blue);
        green = (ImageButton) findViewById(R.id.green);
        red = (ImageButton) findViewById(R.id.red);
        yellow = (ImageButton) findViewById(R.id.yellow);

    }

    @Override
    protected void onResume() {
        super.onResume();
        play(logic.getNewComputerMoves());

    }

    private void play(final int[] moves) {
        Log.d("Simon: ", moves.toString());
        new CountDownTimer(1000*(logic.NUM_OF_MOVES+1), 1000){
            int i=0;
            View lastBtn = null;
            int lastBackground = 0;

            @Override
            public void onTick(long millisUntilFinished) {

                if(i<=logic.NUM_OF_MOVES){

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
        new CountDownTimer(300,300){
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
                Log.d("Simon: ", "win win win win win win");
                Intent intent = new Intent();
                intent.putExtra("simonChallenge",true);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        else{
            //TODO: fail!!!
            button.setBackgroundResource(R.drawable.ic_fail);
        }
    }
}
