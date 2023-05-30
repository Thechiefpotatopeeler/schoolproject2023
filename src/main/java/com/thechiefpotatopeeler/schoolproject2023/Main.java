package com.thechiefpotatopeeler.schoolproject2023;

import com.thechiefpotatopeeler.schoolproject2023.board.Board;
import com.thechiefpotatopeeler.schoolproject2023.display.TextUI;

/**
 * The main class of the program
 * */
public class Main {

    public static void main(String[] args) {

        Board.fillBlankBoard(50,30);
        Board.setCell(0,10,true);
        Board.setCell(0,11,true);
        Board.setCell(0,12,true);
        //Board.setCell(5,12,true);
        TextUI.printBoard();
        Board.advanceGenerations(10);
        //TextUI.printBoard();
    }
}
