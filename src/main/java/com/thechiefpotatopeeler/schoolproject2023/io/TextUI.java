package com.thechiefpotatopeeler.schoolproject2023.io;

import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import com.thechiefpotatopeeler.schoolproject2023.io.gui.UIApplication;

import java.util.ArrayList;

import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;

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
        for (ArrayList<Boolean> row : BoardHandler.currentBoard.getBoard()) {
            for (Boolean value : row) {
                System.out.print(value ? "X" : ".");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
