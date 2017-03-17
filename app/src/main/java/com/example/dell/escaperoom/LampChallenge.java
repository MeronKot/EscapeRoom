package com.example.dell.escaperoom;

import android.content.Context;
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

public class LampChallenge extends AppCompatActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mLight;
    private static String TAG = "LampChallenge";
    private ConstraintLayout lampChallenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_challenge);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lampChallenge = (ConstraintLayout)findViewById(R.id.lampLayout);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mLight,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.values[0] < 20)
            lampChallenge.setBackgroundResource(R.drawable.onlight);
        if(event.values[0] > 500)
            lampChallenge.setBackgroundResource(R.drawable.offlight);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
