package thechiefpotatopeeler.schoolproject2023.board;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The class which handles the game's board
 * */

public class Board {

    public static ArrayList<ArrayList<Boolean>> boardArray;

    public Board(){

    }

    /**
     * This function generates the board according to provided dimensions and fills it with blank cells
     *
     * @param dimX The x dimension of the board it generates
     * @param dimY The y dimension of the board it generates
     * */
    public static void fillBlankBoard(int dimX, int dimY){
        boardArray = new ArrayList<ArrayList<Boolean>>(Collections.nCopies(dimX, new ArrayList<Boolean>(Collections.nCopies(dimY,false))));
    }
}
