package com.example.dell.escaperoom;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Room extends AppCompatActivity {

    private ImageButton imageButton;
    private ImageButton lamp;
    private ImageButton puzzle;
    private ImageButton simon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imageButton = (ImageButton)findViewById(R.id.topRightPic);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,ChemistryChallenge.class);
                startActivity(intent);
            }
        });

        lamp = (ImageButton)findViewById(R.id.lamp);
        lamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,LampChallenge.class);
                startActivity(intent);
            }
        });

        puzzle = (ImageButton) findViewById(R.id.puzzleBtn);
        puzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,PuzzleActivity.class);
                startActivity(intent);
            }
        });

        simon = (ImageButton) findViewById(R.id.simonBtn);
        simon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Room.this,SimonSaysActivity.class);
                startActivity(intent);
            }
        });
    }
}
