package com.example.dell.escaperoom.Logic;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dell.escaperoom.PuzzleActivity;

/**
 * Created by yaelgersh on 16/03/2017.
 */

public class PuzzleLogic {

    public static final int LENGTH = 3;
    private PuzzleBoard board;
    private Context context;
    private WinListener listener;


    public PuzzleLogic(Context context){
        this.context = context;
        board = new PuzzleBoard(LENGTH, LENGTH, context);
    }

    public Tile getTile(int row, int col){
        return board.getTile(row,col);
    }

    public void buttonClicked(int row, int col) {
        int value = board.getTile(row,col).getValue();
        if(value != 0){
            int[] temp =board.findZero();
            int i = temp[0];
            int j = temp[1];

            if((i == row && j == col + 1) || (i == row && j == col - 1)
                    || (i == row + 1 && j == col) || (i == row - 1 && j == col)){
                board.swap(row, i, col, j);

                if(board.checkWin()){
                    listener.onWin();
                }
            }

        }

    }

    public void setListener(WinListener listener) {
        this.listener = listener;
    }

    public void stopListener() {
        this.listener = null;
    }

    public interface WinListener {
        void onWin();
    }
}



/*if(value != 0){
            if (row == 0) {
                check(row + 1, col);
                if (col == 0) {
                    check(row, col + 1);
                } else if (col == LENGTH - 1) {
                    check(row, col - 1);
                } else {
                    check(row, col + 1);
                    check(row, col - 1);
                }
            } else if (row == LENGTH - 1) {
                check(row - 1, col);
                if (col == 0) {
                    check(row, col + 1);
                } else if (col == LENGTH - 1) {
                    check(row, col - 1);
                } else {
                    check(row, col + 1);
                    check(row, col - 1);
                }
            } else if (col == 0) {
                check(row, col + 1);
                check(row + 1, col);
                check(row - 1, col);
            } else if (col == LENGTH - 1) {
                check(row, col - 1);
                check(row + 1, col);
                check(row - 1, col);
            } else {
                check(row, col + 1);
                check(row, col - 1);
                check(row + 1, col);
                check(row - 1, col);
            }

        }
        else
            return;*/