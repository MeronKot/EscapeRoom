package com.example.dell.escaperoom.Database.DBObjects;

import com.example.dell.escaperoom.Database.PlayerHandler;

/**
 * Created by yaelgersh on 25/03/2017.
 */

public class Player {


    private String id;
    private String name;
    private String time;
    private int level1 = 0;
    private int level2 = 0;
    private int level3 = 0;
    private int level4 = 0;
    private int level5 = 0;
    private int hints;


    public Player(){
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        PlayerHandler.getInstance().updatePlayer();
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
        PlayerHandler.getInstance().updatePlayer();
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
        PlayerHandler.getInstance().updatePlayer();
    }

    public int getLevel3() {
        return level3;
    }

    public void setLevel3(int level3) {
        this.level3 = level3;
        PlayerHandler.getInstance().updatePlayer();
    }

    public int getLevel4() {
        return level4;
    }

    public void setLevel4(int level4) {
        this.level4 = level4;
        PlayerHandler.getInstance().updatePlayer();
    }

    public int getLevel5() {
        return level5;
    }

    public void setLevel5(int level5) {
        this.level5 = level5;
        PlayerHandler.getInstance().updatePlayer();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        PlayerHandler.getInstance().updatePlayer();
    }

    public int getHints() {
        return hints;
    }

    public void setHints(int hints) {
        this.hints = hints;
        PlayerHandler.getInstance().updatePlayer();
    }

    public boolean isWinner() {
        if(level1 == 1 && level2 == 1 && level3 == 1 && level4 == 1 && level5 == 1)
            return true;
        return false;
    }
}
