package com.example.dell.escaperoom;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dell.escaperoom.Database.PlayerHandler;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String userName;
    private String uid;
    private static final int RC_SIGN_IN = 0;

    private ImageButton logout;
    private static ImageButton start;
    private ImageButton records;
    private ImageButton instructions;

    private MediaPlayer doorSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // already signed in
            uid = mAuth.getCurrentUser().getUid();
            userName = mAuth.getCurrentUser().getDisplayName();
            Log.d("Auth", "id: " + uid + " name: " + userName);
            PlayerHandler.getInstance().setFirstUpdate(true);
            initActivity();

        } else {
            // not signed in
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setProviders(AuthUI.FACEBOOK_PROVIDER,
                            AuthUI.GOOGLE_PROVIDER).build(), RC_SIGN_IN);
        }
    }

    public void initActivity(){
        PlayerHandler.getInstance().setPlayer(uid, userName);

        doorSound = MediaPlayer.create(this.getApplicationContext(), R.raw.door_open1);

        records = (ImageButton) findViewById(R.id.records);

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
                startActivity(intent);
            }
        });
        instructions = (ImageButton) findViewById(R.id.instructions);
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InstructionActivity.class);
                startActivity(intent);
            }
        });

        start = (ImageButton) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView openDoorImg = (ImageView) findViewById(R.id.openDoor);
                //final RelativeLayout background = (RelativeLayout) findViewById(R.id.activity_main);
                hide(true);
                //background.setBackgroundResource(R.drawable.livingroom);
                openDoorImg.setVisibility(View.VISIBLE);
                openDoorImg.bringToFront();
                openDoorImg.setBackgroundResource(R.drawable.open_door);
                ((AnimationDrawable)openDoorImg.getBackground()).start();
                doorSound.start();
                new CountDownTimer(1200,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(MainActivity.this, Room.class);
                        startActivity(intent);
                        hide(false);

                        openDoorImg.setVisibility(View.INVISIBLE);
                        //background.setBackgroundResource(R.drawable.background);
                    }
                }.start();




            }
        });

        //startValid(false);
        logout = (ImageButton) findViewById(R.id.logout_button);
        //logout.setClickable(true);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                                .setProviders(AuthUI.FACEBOOK_PROVIDER,
                                        AuthUI.GOOGLE_PROVIDER).build(), RC_SIGN_IN);
                    }
                });
            }
        });

    }
    private void hide(boolean toHide) {
        if(toHide){
            start.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.INVISIBLE);
            instructions.setVisibility(View.INVISIBLE);
            records.setVisibility(View.INVISIBLE);
        }
        else{
            start.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            instructions.setVisibility(View.VISIBLE);
            records.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        ImageView openDoor = (ImageView) findViewById(R.id.openDoor);
        openDoor.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                //mAuth.getCurrentUser().getEmail();
                uid = mAuth.getCurrentUser().getUid();
                userName = mAuth.getCurrentUser().getDisplayName();
                Log.d("Auth", "id: " + uid +" name: "+ userName);

                PlayerHandler.getInstance().setFirstUpdate(true);
                initActivity();
            }
            else{

            }
        }
    }
}

