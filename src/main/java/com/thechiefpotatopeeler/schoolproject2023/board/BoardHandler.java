package com.thechiefpotatopeeler.schoolproject2023.board;

import java.util.ArrayList;

/**
 * The class which handles the game's board
 * */

public class BoardHandler {

    public static Board currentBoard = new Board();

    private static int generationCount=0;

    public static final String OUT_OF_BOUNDS_MESSAGE = "Cell out of bounds";

    /**
     *  The method that actually generates a new generation
     *
     * @Return An ArrayList<> of ArrayList<>s of Booleans
     * */
    public static ArrayList<ArrayList<Boolean>> generateNewGeneration() {
        ArrayList<ArrayList<Boolean>> newBoard = new ArrayList<>();
        for (int i = 0; i < currentBoard.getWidth(); i++) {
            ArrayList<Boolean> newRow = new ArrayList<>();
            for (int j = 0; j < currentBoard.getHeight(); j++) {
                boolean cell = currentBoard.getCell(j,i);
                int adjacentCells = currentBoard.countLivingCellNeighbours(j, i);
                if (cell && (adjacentCells < 2 || adjacentCells > 3)) {
                    newRow.add(false);
                } else if (!cell && adjacentCells == 3) {
                    newRow.add(true);
                } else {
                    newRow.add(cell);
                }
            }
            newBoard.add(newRow);
        }
        return newBoard;
    }

    /**
     *  Method that advances the board a certain number of generations.
     *
     * @param generations The number of generations to advance
     * */
    public static void advanceGenerations(int generations){
        for(int i=0; i<generations;i++){
            generationCount++;
            //System.out.println(String.format("Generation %s:", generationCount));
            currentBoard.replaceBoard(generateNewGeneration());
            //TextUI.printBoard();
        }
    }

    /**
     *  Returns true if the cell is on the edge of the board
     *  Currently not in use
     *
     * @return Returns true or false ^^^^^^^^^^^^^^^^^
     *
     * @param dimX X dimension of cell
     * @param dimY Y dimension of cell
     * */
    public static boolean isEdgeCell(int dimX, int dimY){
        if(dimY==currentBoard.size()||dimY==0||dimX==0||dimY==currentBoard.get(dimX).size()) return true;
        else return false;
    }
    /**
     * Method that returns the number of living neighbouring cells
     * @return Returns an integer value which is the number of cells
     * @param dimX The X dimension of the cell
     * @param dimY the Y dimension of the cell
     * */
//


    /**
     * Temporary method
     * */
    public static Board getBoard(){
        return currentBoard;
    }



}
