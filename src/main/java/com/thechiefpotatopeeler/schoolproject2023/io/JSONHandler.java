package com.thechiefpotatopeeler.schoolproject2023.io;

import com.thechiefpotatopeeler.schoolproject2023.board.Board;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
import java.util.ArrayList;

/**
 * @author Thomas Jackson
 *
 * The class that loads boards into JSON
 * */
public class JSONHandler {

    public Board loadBoard(String board) throws ParseException, ClassCastException {
        JSONParser parser = new JSONParser();
        JSONObject boardJSON = (JSONObject) parser.parse(board);
        JSONArray rows = (JSONArray) boardJSON.get("board");
        ArrayList<ArrayList<Boolean>> boardArray = new ArrayList<>();
        for(Object row : rows){
            JSONArray rowArray = (JSONArray) row;
            ArrayList<Boolean> column = new ArrayList<>();
            for(Object cell : rowArray){
                column.add((Boolean) cell);
            }
            boardArray.add(column);
        }
        return new Board(boardArray);
    }

    public JSONObject exportBoard(Board board) {
        JSONObject boardJSON = new JSONObject();
        JSONArray rows = new JSONArray();
        for(ArrayList<Boolean> column : board.getBoard()){
            JSONArray row = new JSONArray();
            for(Boolean cell : column){
                row.add(cell);
            }
            rows.add(row);
        }
        boardJSON.put("board", rows);

        return boardJSON;
    }
}
