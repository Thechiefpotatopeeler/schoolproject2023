package thechiefpotatopeeler.schoolproject2023.board;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The class which handles the game's board
 * */

public class Board {

    private static ArrayList<ArrayList<Boolean>> currentBoard;

    public static final String OUT_OF_BOUNDS_MESSAGE = "Cell out of bounds";

    public static ArrayList<ArrayList<Boolean>> generateNewGeneration(){
        ArrayList<ArrayList<Boolean>> newBoard = currentBoard;
        for(ArrayList<Boolean> row : currentBoard){
            for(Boolean cell : row){
                int cellY = currentBoard.indexOf(row);
                int cellX = row.indexOf(cell);
                if(!cell){//When the cell is dead
                    if(countLivingCellNeighbours(cellX,cellY)==3){
                        newBoard.get(cellY).set(cellX, true);
                    }
                } else{//When the cell is alive
                    if(countLivingCellNeighbours(cellX,cellY)<2||countLivingCellNeighbours(cellX,cellY)>4){
                        System.out.println(String.format("This cell should die: %d,%d because it has %d neighbours",cellX,cellY, countLivingCellNeighbours(cellY,cellX)));
                        newBoard.get(cellX).set(cellY, false);
                    }
                }
            }
        }
        return newBoard;
    }

    public static void advanceGenerations(int generations){
        for(int i=0; i<generations;i++){
            currentBoard = generateNewGeneration();
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
        for (int i = 0; i < dimX; i++) {
            ArrayList<Boolean> row = new ArrayList<>(Collections.nCopies(dimY, false));
            currentBoard.add(row);
        }
    }

    public static boolean isEdgeCell(int dimX, int dimY){
        if(dimY>=currentBoard.size()||dimY<0||dimX<0||dimY>=currentBoard.get(dimX).size()) return true;
        else return false;
    }

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
            currentBoard.get(dimY).set(dimX,!currentBoard.get(dimX).get(dimY));
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
            System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
    }
}
