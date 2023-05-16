package thechiefpotatopeeler.schoolproject2023;

import thechiefpotatopeeler.schoolproject2023.board.Board;

import java.util.ArrayList;

/**
 * The main class of the program
 * */
public class Main {

    public static void main(String[] args) {

        Board.fillBlankBoard(5,5);
        Board.setCell(1,1,true);
    }
}
