package thechiefpotatopeeler.schoolproject2023.display;

import thechiefpotatopeeler.schoolproject2023.board.Board;

import java.util.ArrayList;

public class TextUI {

    public static void printBoard(){
        for (ArrayList<Boolean> row : Board.getBoard()) {
            for (Boolean value : row) {
                System.out.print(value ? "1" : "0");
            }
            System.out.println();
        }
    }
}
