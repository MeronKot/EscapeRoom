package com.example.dell.escaperoom.Database;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dell.escaperoom.Database.DBObjects.Player;
import com.example.dell.escaperoom.Logic.GifImageView;
import com.example.dell.escaperoom.R;

public class WinActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        nameText = (TextView)findViewById(R.id.name);
        time = (TextView)findViewById(R.id.playerTime);

        //nameText.setText(getIntent().getStringExtra("name"));
       // time.setText(getIntent().getStringExtra("time"));
        nameText.setText(PlayerHandler.getInstance().getPlayer().getName());
        time.setText(PlayerHandler.getInstance().getPlayer().getTime());
        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.animatedfireworksimage);
    }
}
