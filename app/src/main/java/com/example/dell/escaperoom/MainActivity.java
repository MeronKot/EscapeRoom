package com.example.dell.escaperoom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private ImageButton logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userName = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        logIn = (ImageButton)findViewById(R.id.logIn);
        logIn.setPadding(0,0,0,0);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                startActivity(intent);
            }
        });
    }
}
