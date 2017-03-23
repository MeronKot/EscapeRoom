package com.example.dell.escaperoom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dell.escaperoom.Logic.PuzzleLogic;

public class PuzzleActivity extends AppCompatActivity implements PuzzleLogic.WinListener {

    private PuzzleLogic logic;

    private LinearLayout gameTable;
    private LinearLayout colLayout;
    private DisplayMetrics metrics;
    private WindowManager manager;
    private Display display;
    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        initTheBoard();
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
                width  = metrics.widthPixels /(PuzzleLogic.LENGTH /*+ 1*/);
                height = metrics.heightPixels/(PuzzleLogic.LENGTH + 1);
                ViewGroup.LayoutParams buttonParams = new ViewGroup.LayoutParams(width, height);
                colLayout.addView(logic.getTile(row, col), buttonParams);

                final int finalCol = col;
                final int finalRow = row;
                logic.getTile(row, col).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO:
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
}
