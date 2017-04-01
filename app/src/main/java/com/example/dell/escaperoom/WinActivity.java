package com.example.dell.escaperoom;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dell.escaperoom.Database.DBObjects.Player;
import com.example.dell.escaperoom.Database.PlayerHandler;
import com.example.dell.escaperoom.Logic.GifImageView;
import com.example.dell.escaperoom.R;

public class WinActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        nameText = (TextView)findViewById(R.id.name);
        time = (TextView)findViewById(R.id.playerTime);

        //nameText.setText(getIntent().getStringExtra("name"));
       // time.setText(getIntent().getStringExtra("time"));
        nameText.setText("Congratulations "+PlayerHandler.getInstance().getPlayer().getName());
        time.setText(PlayerHandler.getInstance().getPlayer().getTime());
        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.animatedfireworksimage);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.fireworks);
        //mp.start();
    }
}
