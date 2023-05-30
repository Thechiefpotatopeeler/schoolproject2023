package com.thechiefpotatopeeler.schoolproject2023.display;

import com.thechiefpotatopeeler.schoolproject2023.board.Board;

import java.util.ArrayList;

public class TextUI {

    public static void printBoard(){
        for (ArrayList<Boolean> row : Board.getBoard()) {
            for (Boolean value : row) {
                System.out.print(value ? "X" : ".");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
