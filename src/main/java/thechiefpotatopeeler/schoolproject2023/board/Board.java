package thechiefpotatopeeler.schoolproject2023.board;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The class which handles the game's board
 * */

public class Board {

    private static ArrayList<ArrayList<Boolean>> boardArray;

    public static final String OUT_OF_BOUNDS_MESSAGE = "Cell out of bounds";

    public Board(){

    }

    /**
     * This function generates the board according to provided dimensions and fills it with blank cells
     *
     * @param dimX The x dimension of the board it generates
     * @param dimY The y dimension of the board it generates
     * */
    public static void fillBlankBoard(int dimX, int dimY) {
        boardArray = new ArrayList<>();
        for (int i = 0; i < dimX; i++) {
            ArrayList<Boolean> row = new ArrayList<>(Collections.nCopies(dimY, false));
            boardArray.add(row);
        }
    }
    /**
     * Method that gets the value of the cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public static @Nullable Boolean getCell(int dimX, int dimY){
        try{
            return boardArray.get(dimY).get(dimX);
        } catch(IndexOutOfBoundsException e){
            System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
        return null;
    }

    public static ArrayList<ArrayList<Boolean>> getBoard(){
        return boardArray;
    }

    /**
     * Method that toggles the value of the cell
     *
     * @param dimX Integer x value of the cell
     * @param dimY Integer y value of the cell
     * */
    public static void toggleCell(int dimX, int dimY){
        try{
            boardArray.get(dimY).set(dimX,!boardArray.get(dimX).get(dimY));
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
            boardArray.get(dimY).set(dimX,state);
            //System.out.println(boardArray.get(dimX));
        } catch(IndexOutOfBoundsException e){
            System.out.println(OUT_OF_BOUNDS_MESSAGE);
        }
    }
}
