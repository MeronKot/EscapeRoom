package com.example.dell.escaperoom;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ChemistryChallenge extends AppCompatActivity {

    private static final int NUM_OF_TEMPS = 3;
    private SeekBar hotWater;
    private SeekBar ice;
    private SeekBar roomTemp;
    private boolean [] tempCorrect;
    private static final String TAG = "ChemistryChallenge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry_challenge);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tempCorrect = new boolean[NUM_OF_TEMPS];
        for(boolean b : tempCorrect)
            b = false;
        hotWater = (SeekBar)findViewById(R.id.hotWater);
        ice = (SeekBar)findViewById(R.id.ice);
        roomTemp = (SeekBar)findViewById(R.id.roomTemp);
        setListeners();
    }

    private void setListeners() {
        hotWater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() >= 98 && seekBar.getProgress() <= 100)
                {
                    tempCorrect[0] = true;
                    Log.i(TAG,"hot water temperature is correct");
                    checkIfDone();
                }else{
                    tempCorrect[0] = false;
                }
            }
        });

        ice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() >= 64 && seekBar.getProgress() <= 66)
                {
                    tempCorrect[1] = true;
                    Log.i(TAG,"ice temperature is correct");
                    checkIfDone();
                }else{
                    tempCorrect[1] = false;
                }
            }
        });

        roomTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG,"progress   progress " +  seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() >= 46 && seekBar.getProgress() <= 48)
                {
                    tempCorrect[2] = true;
                    Log.i(TAG,"room temperature is correct");
                    checkIfDone();
                }else{
                    tempCorrect[2] = false;
                }
            }
        });
    }

    private void checkIfDone() {
        int i = 0;
        for(boolean b : tempCorrect)
            if(b)
                i++;

        if (i == NUM_OF_TEMPS) {
            Toast.makeText(this, "Good! chemistry is our life", Toast.LENGTH_LONG).show();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra("tempChallenge", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }, 3000);
        }
    }
}
