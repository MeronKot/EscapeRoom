package com.example.dell.escaperoom;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

public class ChemistryChallenge extends AppCompatActivity {

    private SeekBar hotWater;
    private SeekBar ice;
    private SeekBar roomTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemistry_challenge);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        hotWater = (SeekBar)findViewById(R.id.hotWater);
        ice = (SeekBar)findViewById(R.id.ice);
        roomTemp = (SeekBar)findViewById(R.id.roomTemp);
        setListeners();
    }

    private void setListeners() {
        hotWater.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (progress == 100)
                        Toast.makeText(getApplicationContext(),"great",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0)
                    Toast.makeText(getApplicationContext(),"great",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        roomTemp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 20)
                    Toast.makeText(getApplicationContext(),"great",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
