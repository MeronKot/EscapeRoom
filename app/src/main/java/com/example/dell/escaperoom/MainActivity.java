package com.example.dell.escaperoom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.facebook.CallbackManager;

public class MainActivity extends AppCompatActivity implements LogInFragment.LogInListener {

    private LogInFragment FLogIn = new LogInFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //FLogIn = new LogInFragment();
        FLogIn.SetListener(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_layout, FLogIn);
        ft.commit();
    }

    @Override
    public void onLogInSuccess() {
        ImageButton start = (ImageButton) findViewById(R.id.start);
        start.setVisibility(View.VISIBLE);

        ImageButton instructions = (ImageButton) findViewById(R.id.instructions);
        instructions.setVisibility(View.VISIBLE);

        ImageButton records = (ImageButton) findViewById(R.id.records);
        records.setVisibility(View.VISIBLE);
    }
}
