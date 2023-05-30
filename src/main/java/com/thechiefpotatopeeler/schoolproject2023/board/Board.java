package com.thechiefpotatopeeler.schoolproject2023.board;

import com.thechiefpotatopeeler.schoolproject2023.display.TextUI;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The class which handles the game's board
 * */

public class Board {

    private static ArrayList<ArrayList<Boolean>> currentBoard;

    private static int generationCount=0;

    public static final String OUT_OF_BOUNDS_MESSAGE = "Cell out of bounds";

    /**
     *  The method that actually generates a new generation
     *
     * @Return An ArrayList<> of ArrayList<>s of Booleans
     * */
    public static ArrayList<ArrayList<Boolean>> generateNewGeneration() {
        ArrayList<ArrayList<Boolean>> newBoard = new ArrayList<>();
        for (int i = 0; i < currentBoard.size(); i++) {
            ArrayList<Boolean> newRow = new ArrayList<>();
            for (int j = 0; j < currentBoard.get(i).size(); j++) {
                boolean cell = currentBoard.get(i).get(j);
                int adjacentCells = countLivingCellNeighbours(j, i);
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
            System.out.println(String.format("Generation %s:", generationCount));
            currentBoard = generateNewGeneration();
            TextUI.printBoard();
        }
    }

    /**
     * This function generates the board according to provided dimensions and fills it with blank cells
     *
     * @param dimX The x dimension of the board it generates
     * @param dimY The y dimension of the board it generates
     * */
    public static void fillBlankBoard(int dimX, int dimY) {
        currentBoard = new ArrayList<>();
        for (int i = 0; i < dimY; i++) {
            ArrayList<Boolean> row = new ArrayList<>(Collections.nCopies(dimX, false));
            currentBoard.add(row);
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
    public static int countLivingCellNeighbours(int dimX, int dimY){
        int count=0;
        if(Boolean.TRUE.equals(getCell(dimX, dimY - 1))) count++;
        if(Boolean.TRUE.equals(getCell(dimX, dimY + 1))) count++;
        if(Boolean.TRUE.equals(getCell(dimX - 1, dimY))) count++;
        if(Boolean.TRUE.equals(getCell(dimX - 1, dimY - 1))) count++;
        if(Boolean.TRUE.equals(getCell(dimX - 1, dimY + 1))) count++;
        if(Boolean.TRUE.equals(getCell(dimX + 1, dimY))) count++;
        if(Boolean.TRUE.equals(getCell(dimX + 1, dimY - 1))) count++;
        if(Boolean.TRUE.equals(getCell(dimX + 1, dimY + 1))) count++;
        return count;
    }
    /**
     * Method that gets the value of the cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public static @Nullable Boolean getCell(int dimX, int dimY){
        try{
            return currentBoard.get(dimY).get(dimX);
        } catch(IndexOutOfBoundsException e){
            //System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
        return null;
    }

    /**
     * Temporary method
     * */
    public static ArrayList<ArrayList<Boolean>> getBoard(){
        return currentBoard;
    }

    /**
     * Method that toggles the value of the cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public static void toggleCell(int dimX, int dimY){
        try{
            currentBoard.get(dimY).set(dimX,!currentBoard.get(dimY).get(dimX));
        } catch(IndexOutOfBoundsException e){
            System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
    }
    /**
     * Method that sets the value of the cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public static void setCell(int dimX, int dimY, boolean state){
        try{
            currentBoard.get(dimY).set(dimX,state);
            //System.out.println(currentBoard().get(dimX));
        } catch(IndexOutOfBoundsException e){
           // System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
    }
}
