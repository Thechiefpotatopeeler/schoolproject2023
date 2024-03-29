package com.thechiefpotatopeeler.schoolproject2023.board;

import java.util.ArrayList;

/**
 * @author Thomas Jackson
 *
 * The class which handles the game's board
 * */
public class BoardHandler {

    public static Board currentBoard = new Board();

    public static int xSize,ySize;

    private static int generationCount=0;

    public static final String OUT_OF_BOUNDS_MESSAGE = "Cell out of bounds";

    /**
     *  The method that actually generates a new generation
     *
     * @return An ArrayList<> of ArrayList<>s of Booleans
     * */
    public static Board generateNewGeneration() {
        ArrayList<ArrayList<Boolean>> newColumn = new ArrayList<>();
        //Recurse through the board
        for (int i = 0; i < currentBoard.getWidth(); i++) {
            ArrayList<Boolean> newRow = new ArrayList<>();
            for (int j = 0; j < currentBoard.getHeight(); j++) {

                boolean cell = Boolean.TRUE.equals(currentBoard.getCell(j, i));
                int adjacentCells = currentBoard.countLivingCellNeighbours(j, i);
                //Applies the rules of the game

                if (cell && (adjacentCells < 2 || adjacentCells > 3)) {
                    newRow.add(false);
                } else if (!cell && adjacentCells == 3) {
                    newRow.add(true);
                } else {
                    newRow.add(cell);
                }
            }
            newColumn.add(newRow);
        }
        Board newBoard = new Board(newColumn);
        return newBoard;
    }

    /**
     *  Method that advances the board a certain number of generations.
     *
     * @param generations The number of generations to advance
     * @param onGenerationAdvanced The method to run after each generation is advanced (currently unused but left in for future use or other implementations)
     * */
    public static void advanceGenerations(int generations,Runnable onGenerationAdvanced) {
        for (int i = 0; i < generations; i++) {
            generationCount++;
//            ArrayList<ArrayList<Boolean>> newBoard = generateNewGeneration();
//            currentBoard.replaceBoard(newBoard);
            currentBoard = generateNewGeneration();
            onGenerationAdvanced.run();
        }
    }

    /*
     *  Returns true if the cell is on the edge of the board
     *  Currently not in use
     *
     * @return Returns true or false ^^^^^^^^^^^^^^^^^
     *
     * @param dimX X dimension of cell
     * @param dimY Y dimension of cell
     * */
    /*
    public static boolean isEdgeCell(int dimX, int dimY){
        return dimY == currentBoard.size() || dimY == 0 || dimX == 0 || dimY == currentBoard.get(dimX).size();
    }*/
//


    /**
     * Temporary method
     * */
    public static Board getBoard(){
        return currentBoard;
    }

    /*
    * Generic methods for setting and getting the size of the board
    * */

    /**
     * Sets the size of the board
     * */
    public static void setSize(int x, int y) {
    	xSize = x;
    	ySize = y;
    }

    /**
     * Sets the x dimension of the board
     * @param x the value for x
     * */
    public static void setXSize(int x) {
    	xSize = x;
    }

    /**
     * Sets the y dimension of the board
     * @param y the value for y
     * */
    public static void setYSize(int y) {
    	ySize = y;
    }

    /**
     * Gets the width of the board
     * @return the width
     * */
    public static int getWidth() {
    	return xSize;
    }

    /**
     * Gets the height of the board
     * @return the height
     * */
    public static int getHeight() {
    	return ySize;
    }
}
