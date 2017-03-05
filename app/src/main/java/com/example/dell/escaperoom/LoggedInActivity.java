package com.example.dell.escaperoom;

import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class LoggedInActivity extends AppCompatActivity {

    private ImageButton start;
    private ImageButton instructions;
    private ImageButton records;
    private ImageButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        start = (ImageButton)findViewById(R.id.start);
        start.setPadding(0,0,0,15);
        instructions = (ImageButton)findViewById(R.id.instructions);
        instructions.setPadding(0,0,0,15);
        records = (ImageButton)findViewById(R.id.records);
        records.setPadding(0,0,0,15);
        logout = (ImageButton)findViewById(R.id.logout);
        logout.setPadding(0,0,0,15);
    }
}
