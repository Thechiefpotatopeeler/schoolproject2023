package com.thechiefpotatopeeler.schoolproject2023.display.gui;

import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import javafx.scene.control.Cell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CellUIComponent  extends StackPane {
    private int cellX, cellY;

    private Rectangle border = new Rectangle(UIApplication.cellUISize, UIApplication.cellUISize);
    private Text text = new Text();

    public CellUIComponent(int cellX, int cellY) {
        this.cellX = cellX;
        this.cellY = cellY;

        //text.setText("O");
        border.setStroke(javafx.scene.paint.Color.BLACK);
        border.setFill(BoardHandler.currentBoard.getCell(cellX, cellY) ? Color.INDIANRED : javafx.scene.paint.Color.DARKGREY);

        getChildren().addAll(border, text);

        setTranslateX(cellX * UIApplication.cellUISize);
        setTranslateY(cellY * UIApplication.cellUISize);
    }

    public void updateCell(boolean cell) {
        if (cell) {
            border.setFill(Color.INDIANRED);
        } else {
            border.setFill(javafx.scene.paint.Color.DARKGREY);
        }
    }

    public int getCellX() {
        return cellX;
    }

    public int getCellY() {
        return cellY;
    }

}
