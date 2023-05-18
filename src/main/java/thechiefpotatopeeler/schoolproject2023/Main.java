package thechiefpotatopeeler.schoolproject2023;

import thechiefpotatopeeler.schoolproject2023.board.Board;
import thechiefpotatopeeler.schoolproject2023.display.TextUI;

import java.util.ArrayList;

/**
 * The main class of the program
 * */
public class Main {

    public static void main(String[] args) {

        Board.fillBlankBoard(50,50);
        Board.setCell(1,4,true);
        TextUI.printBoard();
    }
}
