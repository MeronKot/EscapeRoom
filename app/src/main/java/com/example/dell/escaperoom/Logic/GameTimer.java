package com.example.dell.escaperoom.Logic;

/**
 * Created by yaelgersh on 25/03/2017.
 */

public class GameTimer {

    private int min;
    private int sec;
    private int secCounter = 0;
    private static GameTimer instance = new GameTimer();


    private GameTimer() {
        min = 0;
        sec = 0;
    }

    public int getSecCounter() {
        return secCounter;
    }

    public String toString() {
        String str = "";
        str += (min < 10? "0" + min : "" + min);
        str += ':';
        str += (sec < 10? "0" + sec : "" + sec);
        return str;
    }

    /** increase clock by 1 sec */
    public void tick() {
        secCounter++;
        min = secCounter / 60;
        sec = secCounter % 60;
    }

    public static GameTimer getInstance() {
        return instance;
    }
}
