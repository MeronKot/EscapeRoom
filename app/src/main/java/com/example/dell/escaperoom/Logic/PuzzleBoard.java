package com.example.dell.escaperoom.Logic;

import android.content.Context;
import android.view.View;

/**
 * Created by yaelgersh on 17/03/2017.
 */

public class PuzzleBoard {

    private int rows;
    private int cols;
    private Tile[][] board;
    private int[][] puzzle = {{3,0,2}, {4,1,8}, {6,5,7}};

    public PuzzleBoard(int rows, int cols, Context con) {
        this.cols = cols;
        this.rows = rows;

        board = new Tile[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                board[i][j] = new Tile(con, i, j, puzzle[i][j]);
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public int[] findZero(){
        for(int i=0 ; i < rows ; i++){
            for (int j=0 ; j < cols; j++){
                if(puzzle[i][j]==0){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public void swap(int row, int i, int col, int j) {

        int tempValue = getTile(row, col).getValue();
        getTile(row, col).setValue(getTile(i,j).getValue());
        puzzle[row][col] = puzzle[i][j];
        getTile(i,j).setValue(tempValue);
        puzzle[i][j]=tempValue;
    }

    public boolean checkWin() {
        int current=0;
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < cols ; j++){
                if(puzzle[i][j] != current)
                    return false;
                current++;
            }
        }
        return true;
    }
}
