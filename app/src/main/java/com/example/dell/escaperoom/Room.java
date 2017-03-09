package com.example.dell.escaperoom;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Room extends AppCompatActivity {

    private ImageButton imageButton;
    private RelativeLayout missionContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imageButton = (ImageButton)findViewById(R.id.topRightPic);
        missionContainer = (RelativeLayout)findViewById(R.id.missionContainer);
    }
}
