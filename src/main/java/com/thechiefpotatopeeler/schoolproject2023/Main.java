package com.thechiefpotatopeeler.schoolproject2023;

import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import com.thechiefpotatopeeler.schoolproject2023.display.TextUI;

/**
 * The main class of the program
 * */
public class Main {

    public static void main(String[] args) {

        BoardHandler.currentBoard.fillBlankBoard(50,30);
        BoardHandler.currentBoard.setCell(5,10,true);
        BoardHandler.currentBoard.setCell(5,11,true);
        BoardHandler.currentBoard.setCell(5,12,true);
        BoardHandler.currentBoard.setCell(6,13,true);
        TextUI.printBoard();
        BoardHandler.advanceGenerations(10);
        //TextUI.printBoard();
    }
}
