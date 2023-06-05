package com.thechiefpotatopeeler.schoolproject2023.display;

import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;

import java.util.ArrayList;

public class TextUI {

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
