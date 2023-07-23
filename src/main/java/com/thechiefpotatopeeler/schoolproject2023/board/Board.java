package com.thechiefpotatopeeler.schoolproject2023.board;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Thomas Jackson
 *
 * The board class which is used for handling the game data
 * */
public class Board extends ArrayList<ArrayList<Boolean>>{

    private ArrayList<ArrayList<Boolean>> board;//The nested arrayLists which contain the cell data

    /**
     * This function generates the board according to provided dimensions and fills it with blank cells
     *
     * @param dimX The x dimension of the board it generates
     * @param dimY The y dimension of the board it generates
     * */
    public void fillBlankBoard(int dimX, int dimY) {
        board = new ArrayList<>();
        for (int i = 0; i < dimY; i++) {
            ArrayList<Boolean> row = new ArrayList<>(Collections.nCopies(dimX, false));
            board.add(row);
        }
    }
    /**
     * Method that gets the value of the cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public @Nullable Boolean getCell(int dimX, int dimY){
        try{
            return board.get(dimY).get(dimX);
        } catch(IndexOutOfBoundsException e){
            //System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
        return null;
    }

    /**
     * Method that toggles the value of the cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public void toggleCell(int dimX, int dimY){
        try{
            board.get(dimY).set(dimX,!board.get(dimY).get(dimX));
        } catch(IndexOutOfBoundsException e){
            //System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
    }

    /**
     * Method that sets the value of the cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public void setCell(int dimX, int dimY, boolean state){
        try{
            board.get(dimY).set(dimX,state);
            //System.out.println(currentBoard().get(dimX));
        } catch(IndexOutOfBoundsException e){
            // System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
    }

    /**
     * Method that counts the number of living cells around a cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public int countLivingCellNeighbours(int dimX, int dimY){
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
    * Method that returns the width of the board
    * @return int the width of the board
    * */
   public int getWidth(){
        return board.size();
   }

   /**
    * Method that returns the height of the board
    * @return int the height of the board
    * */
   public int getHeight(){
        return board.get(0).size();
   }

    /**
     * Method that replaces the current board with a new one
     * @param newBoard ArrayList<ArrayList<Boolean>> the new board
     * */
   public void replaceBoard(ArrayList<ArrayList<Boolean>> newBoard){this.board = newBoard;}

    /**
     * Method that returns the current board
     * @return ArrayList<ArrayList<Boolean>> the current board
     * */
    public ArrayList<ArrayList<Boolean>> getBoard(){return board;}
}
