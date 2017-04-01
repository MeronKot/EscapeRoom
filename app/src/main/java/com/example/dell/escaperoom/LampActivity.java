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
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class LampActivity extends AppCompatActivity implements SensorEventListener {

    private static final int NUM_OF_HINTS = 3;
    private String [] hints = {"If it's dark, you need to...",
            "If now you are at day, you need to...",
            "Just turn off the lights or turn them on"};
    private int hintCounter = 0;
    private Button hintButton;
    private Button hide;
    private RelativeLayout hintContainer;
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

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lampChallenge = (ConstraintLayout) findViewById(R.id.lampLayout);
        hintButton = (Button)findViewById(R.id.hint);
        hintContainer = (RelativeLayout)findViewById(R.id.hintContainer);
        hide = (Button)findViewById(R.id.hideInHint);
        setListeners();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    private void setListeners() {
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hintCounter == NUM_OF_HINTS){
                    Toast.makeText(getApplicationContext(),"You don't have hints any more",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(LampActivity.this,HintActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        RoomActivity.onGame=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        RoomActivity.onGame = false;
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
                        Intent intent = new Intent();
                        intent.putExtra("lampChallenge",true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                },3000);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
}
