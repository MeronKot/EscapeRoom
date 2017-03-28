package com.example.dell.escaperoom;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ChemistryChallenge extends AppCompatActivity {

    private static final int NUM_OF_TEMPS = 3,NUM_OF_HINTS = 3;
    private String [] hints = {"One hundred degrees Celsius equals 212 Fahrenheit",
            "Water becomes ice at zero degrees Celsius",
            "Room temperature is twenty degrees Celsius and we need it in Fahrenheit"};
    private int hintCounter = 0;
    private SeekBar hotWater;
    private SeekBar ice;
    private SeekBar roomTemp;
    private Button hintButton;
    private Button hide;
    private boolean [] tempCorrect;
    private RelativeLayout hintContainer;
    private static final String TAG = "ChemistryChallenge";

    private MediaPlayer labSound;
    private boolean sound = false;

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
        hintButton = (Button)findViewById(R.id.hint);
        hintContainer = (RelativeLayout)findViewById(R.id.hintContainer);
        hide = (Button)findViewById(R.id.hideInHint);
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

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hintCounter == NUM_OF_HINTS){
                    Toast.makeText(getApplicationContext(),"You don't have hints any more",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ChemistryChallenge.this,HintActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        Room.onGame = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                sound = true;
                labSound = MediaPlayer.create(ChemistryChallenge.this, R.raw.laboratory);
                while (sound){
                    if(!labSound.isPlaying())
                    {

                        labSound.start();

                    }
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Room.onGame = false;

        if(labSound != null) {
            labSound.stop();
        }
    }
}
