package com.thechiefpotatopeeler.schoolproject2023.display;

import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;

import java.util.ArrayList;

/**
 * Class that handles the UI in text mode.
 * @deprecated
 * */
@Deprecated
public class TextUI {

    /**
     * Method that prints the board to the console.
     * @deprecated
     * */
    @Deprecated
    public static void printBoard(){
        for (ArrayList<Boolean> row : BoardHandler.getBoard().getBoard()) {
            for (Boolean value : row) {
                System.out.print(value ? "X" : ".");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
