package com.example.dell.escaperoom.Logic;

import android.util.Log;

import com.example.dell.escaperoom.Database.PlayerHandler;
import com.example.dell.escaperoom.Room;

/**
 * Created by yaelgersh on 25/03/2017.
 */

public class GameTimer {

    private int hour;
    private int min;
    private int sec;
    private int secCounter = 0;

    private static GameTimer instance = new GameTimer();


    private GameTimer() {
        hour = 0;
        min = 0;
        sec = 0;
    }

    public int getSecCounter() {
        return secCounter;
    }

    public String toString() {
        String str = "";

        str += (hour < 10? "0" + hour : "" + hour);
        str += ':';
        str += (min < 10? "0" + min : "" + min);
        str += ':';
        str += (sec < 10? "0" + sec : "" + sec);

        return str;
    }

    /** increase clock by 1 sec */
    public void tick() {
        secCounter++;
        hour = secCounter / 3600;
        min = secCounter / 60;
        min %= 60 ;
        sec = secCounter % 60;
    }

    public static GameTimer getInstance() {
        return instance;
    }

    public void setTime(String time) {
        String[] times = time.split(":");
        hour = Integer.parseInt(times[0]);
        min = Integer.parseInt(times[1]);
        sec = Integer.parseInt(times[2]);

        secCounter = hour * 3600 + min * 60 + sec;
    }


}
