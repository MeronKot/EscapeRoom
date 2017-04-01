package com.example.dell.escaperoom.Logic;

import java.util.Random;

/**
 * Created by yaelgersh on 15/03/2017.
 */

public class SimonSaysLogic {

    public static final int BLUE = 1;
    public static final int GREEN = 2;
    public static final int RED = 3;
    public static final int YELLOW = 4;

    public static Random random = new Random();
    public static final int NUM_OF_MOVES = 6;
    public static final int NUM_OF_COLORS = 4;

    private int[] computerMoves = new int[NUM_OF_MOVES];
    private int move = 1;

    public int getMove(){return move;}

    public int[] getNewComputerMoves(){
        move=1;
        computerMoves[0]=0;
        for(int i=1; i < NUM_OF_MOVES ; i++){
            computerMoves[i] = random.nextInt(NUM_OF_COLORS)+1;
        }
        return computerMoves;
    }

    public boolean buttonPressed(int button){

        if(move >= NUM_OF_MOVES)
        {
            move = 1;
            return false;
        }

        if(computerMoves[move] == button)
        {
            move++;
            return true;
        }

        move = 1;
        return false;

    }





}
