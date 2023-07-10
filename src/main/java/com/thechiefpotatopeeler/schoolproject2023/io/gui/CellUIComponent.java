package com.thechiefpotatopeeler.schoolproject2023.io.gui;

import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * The User interface implementation of a cell
 * */
public class CellUIComponent extends StackPane {
    private int cellX, cellY;//The coordinates which correspond to the cell's position on the board
    private Rectangle border = new Rectangle(UIApplication.cellUISize, UIApplication.cellUISize);//The border around the cell
    private Text text = new Text();//The text inside the cell

    /**
     * The constructor for the cell
     * @param cellX The x coordinate of the cell
     * @param cellY The y coordinate of the cell
     * */
    public CellUIComponent(int cellX, int cellY) {
        this.cellX = cellX;
        this.cellY = cellY;
        if(UIApplication.colourBlindMode) this.text.setText(BoardHandler.currentBoard.getCell(cellX, cellY) ? "O" : "X");

        //text.setText("O");
        border.setStroke(javafx.scene.paint.Color.BLACK);
        border.setFill(BoardHandler.currentBoard.getCell(cellX, cellY) ? Color.INDIANRED : javafx.scene.paint.Color.DARKGREY);

        getChildren().addAll(border, text);

        setTranslateX(cellX * UIApplication.cellUISize);
        setTranslateY(cellY * UIApplication.cellUISize);
    }
    /**
     * Updates the cell's color depending on whether it is true or false
     * @param cell The boolean state of the cell
     * */
    public void updateCell(boolean cell) {
        if(UIApplication.colourBlindMode) this.text.setText(BoardHandler.currentBoard.getCell(cellX, cellY) ? "O" : "X");
        if (cell) {
            border.setFill(Color.INDIANRED);
        } else {
            border.setFill(javafx.scene.paint.Color.DARKGREY);
        }
    }

    /**
     * Gets the x coordinate of the cell
     * @return The x coordinate of the cell
     * */
    public int getCellX() {
        return cellX;
    }

/**
     * Gets the y coordinate of the cell
     * @return The y coordinate of the cell
     * */
    public int getCellY() {
        return cellY;
    }

}
