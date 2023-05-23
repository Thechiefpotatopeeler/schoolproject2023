package com.thechiefpotatopeeler.schoolproject2023;

import com.thechiefpotatopeeler.schoolproject2023.board.Board;
import com.thechiefpotatopeeler.schoolproject2023.display.TextUI;

/**
 * The main class of the program
 * */
public class Main {

    public static void main(String[] args) {

        Board.fillBlankBoard(10,10);
        Board.setCell(1,4,true);
        Board.setCell(1,3,true);
        Board.setCell(2,4,true);
        Board.setCell(1,2,true);
        //Board.setCell(2,3,true);
        TextUI.printBoard();
        Board.advanceGenerations(1);
        TextUI.printBoard();
    }
}
