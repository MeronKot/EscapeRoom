package com.example.dell.escaperoom;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.escaperoom.Logic.PuzzleLogic;

public class PuzzleActivity extends AppCompatActivity implements PuzzleLogic.WinListener {

    private static final int NUM_OF_HINTS = 3;
    private String [] hints = {"Hint no. 1",
            "Hint no. 2",
            "Hint no. 3"};
    private int hintCounter = 0;
    private Button hintButton;
    private Button hide;
    private RelativeLayout hintContainer;
    private PuzzleLogic logic;
    private LinearLayout gameTable;
    private LinearLayout colLayout;
    private DisplayMetrics metrics;
    private WindowManager manager;
    private Display display;
    private int width;
    private int height;
    private MediaPlayer tap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


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
                Intent intent = new Intent(PuzzleActivity.this,HintActivity.class);
                startActivityForResult(intent,hintCounter);
            }
        });

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintContainer.setVisibility(View.INVISIBLE);
            }
        });

        tap = MediaPlayer.create(this.getApplicationContext(), R.raw.tap);
        initTheBoard();


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

    private void initTheBoard() {
        logic = new PuzzleLogic(this);
        gameTable = (LinearLayout) findViewById(R.id.gameTable);


        for(int col = 0 ; col < PuzzleLogic.LENGTH; col++) {
            colLayout = new LinearLayout(this);
            colLayout.setOrientation(LinearLayout.VERTICAL);
            for (int row = 0; row < PuzzleLogic.LENGTH; row++) {

                manager = (WindowManager) this.getSystemService(this.WINDOW_SERVICE);
                display = manager.getDefaultDisplay();
                metrics = new DisplayMetrics();
                display.getMetrics(metrics);
                width  = metrics.widthPixels /(PuzzleLogic.LENGTH )-10;
                height = metrics.heightPixels/(PuzzleLogic.LENGTH )-50;
                ViewGroup.LayoutParams buttonParams = new ViewGroup.LayoutParams(width, height);
                colLayout.addView(logic.getTile(row, col), buttonParams);

                final int finalCol = col;
                final int finalRow = row;
                logic.getTile(row, col).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO:
                        tap.start();
                        logic.buttonClicked(finalRow,finalCol);
                    }
                });

            }
            gameTable.addView(colLayout);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        logic.setListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logic.stopListener();
    }

    @Override
    public void onWin() {
        Intent intent = new Intent();
        intent.putExtra("puzzleChallenge",true);
        setResult(RESULT_OK, intent);
        finish();
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
