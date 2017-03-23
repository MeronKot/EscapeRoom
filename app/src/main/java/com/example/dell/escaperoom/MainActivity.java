package com.example.dell.escaperoom;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ImageButton logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in");

                    /*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();*/

                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        ImageButton start = (ImageButton) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                final ImageView openDoorImg = (ImageView) findViewById(R.id.openDoor);
                openDoorImg.setVisibility(View.VISIBLE);
                openDoorImg.bringToFront();
                openDoorImg.setBackgroundResource(R.drawable.open_door);
                ((AnimationDrawable)openDoorImg.getBackground()).start();*/

                new CountDownTimer(1200,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(MainActivity.this, Room.class);
                        startActivity(intent);

                    }
                }.start();




            }
        });


        logout = (ImageButton) findViewById(R.id.logout_button);
        //logout.setClickable(true);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectFromFacebook();
            }
        });
    }

    public void disconnectFromFacebook() {

        //TODO: Some animation of thinking
        //logout.setClickable(false);

        /*if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        if (FirebaseAuth.getInstance() != null)
            FirebaseAuth.getInstance().signOut();
*/
        if(mAuth != null)
            mAuth.signOut();
        if(LoginManager.getInstance() != null)
            LoginManager.getInstance().logOut();

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
        /*//UnRegistering the Auth. Listener.
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }*/

        /*new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();



            }
        }).executeAsync();
*/
        //nt intent = new Intent(MainActivity.this, LoginActivity.class);
        //startActivity(intent);
        //logout.setClickable(true);
        //finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        ImageView openDoor = (ImageView) findViewById(R.id.openDoor);
        openDoor.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
