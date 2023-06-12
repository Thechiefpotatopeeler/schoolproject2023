package com.thechiefpotatopeeler.schoolproject2023.display.gui;

import com.sun.jdi.connect.Connector;
import com.thechiefpotatopeeler.schoolproject2023.board.Board;
import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class UIApplication extends Application {

    public Stage window;
    public static int cellUISize = 30;

    public static Pane cellGrid;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        stage.setTitle("Conway's Game of Life");
        initGame();
        stage.setScene(this.buildMenuScene());
        stage.show();
    }

    public void enterGame() {
        window.setScene(this.buildGameScene());
    }

    public Scene buildMenuScene(){
        HBox layout = new HBox();

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> enterGame());

        Button exitButton = new Button("Quit");
        exitButton.setOnAction(e -> Platform.exit());

        layout.getChildren().addAll(startButton, exitButton);

        Scene scene = new Scene(layout, 300, 250);
        return scene;
    }

    public Scene buildGameScene(){
        initCellGrid();

        HBox bottomMenu = new HBox();
        Button menuButton = new Button("Main menu");
        menuButton.setOnAction(e -> window.setScene(buildMenuScene()));

        Button advanceGenerationButton = new Button("Advance 1 Generation");
        advanceGenerationButton.setOnAction(e -> {
            BoardHandler.advanceGenerations(1);
            updateCellUI();
        });

        bottomMenu.getChildren().addAll(menuButton, advanceGenerationButton);

        BorderPane layout = new BorderPane();
        layout.setCenter(cellGrid);
        layout.setBottom(bottomMenu);
        return new Scene(layout, 300, 250);
    }

    public static void initGame() {
        BoardHandler.currentBoard.fillBlankBoard(10, 10);
        BoardHandler.currentBoard.setCell(0, 0, true);
        BoardHandler.currentBoard.setCell(1, 0, true);
        BoardHandler.currentBoard.setCell(0, 1, true);
        BoardHandler.currentBoard.setCell(1, 1, true);
    }

    public static void initCellGrid(){
        cellGrid = new Pane();
        Board board = BoardHandler.currentBoard;
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                boolean cell = board.getCell(j,i);
                CellUIComponent cellUI = new CellUIComponent(j,i);
                cellGrid.getChildren().add(cellUI);
            }
        }
        cellGrid.setOnMouseClicked(e -> {
            int x = (int) e.getX() / cellUISize;
            int y = (int) e.getY() / cellUISize;
            BoardHandler.currentBoard.setCell(x, y, !BoardHandler.currentBoard.getCell(x, y));
            updateCellUI();
        });
    }

    public static void updateCellUI() {
        for (int i = 0; i < cellGrid.getChildren().size(); i++) {
            CellUIComponent cellUI = (CellUIComponent) cellGrid.getChildren().get(i);
            cellUI.updateCell(Boolean.TRUE.equals(BoardHandler.currentBoard.getCell(cellUI.getCellX(), cellUI.getCellY())));
        }
    }
}
