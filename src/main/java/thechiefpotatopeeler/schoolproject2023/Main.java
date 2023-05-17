package thechiefpotatopeeler.schoolproject2023;

import thechiefpotatopeeler.schoolproject2023.board.Board;

import java.util.ArrayList;

/**
 * The main class of the program
 * */
public class Main {

    public static void main(String[] args) {

        Board.fillBlankBoard(50,50);
        Board.setCell(1,4,true);

        for (ArrayList<Boolean> row : Board.getBoard()) {
            for (Boolean value : row) {
                System.out.print(value ? "1" : "0");
            }
            System.out.println();
        }
    }
}
