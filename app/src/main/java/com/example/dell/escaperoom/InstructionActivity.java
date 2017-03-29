package com.example.dell.escaperoom;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstructionActivity extends AppCompatActivity {

    //private TextView welcome;
   // private TextView text;
    private Button goodLuck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //welcome = (TextView)findViewById(R.id.title);
        //text = (TextView)findViewById(R.id.instruText);

        //String str = R.string.instructions;

        //welcome.setText("Welcome!");
       // text.setText(str);

        goodLuck = (Button)findViewById(R.id.goodluck);
        goodLuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
