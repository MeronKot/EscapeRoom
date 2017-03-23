package com.example.dell.escaperoom;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class LampChallenge extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mLight;
    private static String TAG = "LampChallenge";
    private ConstraintLayout lampChallenge;
    private boolean startSampling = true;
    private float startValue;
    private boolean finish = false;
    private enum Status {DAY, NIGHT};
    private Status firstStauts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_challenge);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lampChallenge = (ConstraintLayout) findViewById(R.id.lampLayout);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (startSampling) {
            startValue = event.values[0];
            if (startValue <= 20) {
                lampChallenge.setBackgroundResource(R.drawable.onlight);
                firstStauts = Status.NIGHT;
            }
            if (startValue > 20) {
                lampChallenge.setBackgroundResource(R.drawable.offlight);
                firstStauts = Status.DAY;
            }
            startSampling = false;
        }

        if (event.values[0] <= 20 && !finish) {
            if (firstStauts == Status.DAY) {
                lampChallenge.setBackgroundResource(R.drawable.onlight);
                Toast.makeText(this, "Great, when is dark you need to turn on the lights", Toast.LENGTH_LONG).show();
                finish = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.putExtra("lampChallenge",true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                },3000);
            }
        }

        if (event.values[0] > 20 && !finish) {
            if (firstStauts == Status.NIGHT) {
                lampChallenge.setBackgroundResource(R.drawable.offlight);
                Toast.makeText(this, "Great, on day you need to turn off the lights", Toast.LENGTH_LONG).show();
                finish = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                },3000);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
