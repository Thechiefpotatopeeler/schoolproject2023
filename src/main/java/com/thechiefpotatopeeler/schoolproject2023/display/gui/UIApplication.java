package com.thechiefpotatopeeler.schoolproject2023.display.gui;

import com.thechiefpotatopeeler.schoolproject2023.board.Board;
import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;

/**
 * The class which handles the GUI (currently running the application when using GUI as well)
 * */
@SuppressWarnings("unnamed module")
public class UIApplication extends Application {

    public Stage window; // The window which the application runs in

    public static int cellUISize = 30;// The size of the cell UI components

    public static Pane cellGrid;// The grid of cells

    /**
     * The method which runs when the application is started
     * @param stage The stage which the application runs in
     * */
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        stage.setTitle("Conway's Game of Life");
        initGame();
        stage.setScene(this.buildMenuScene());
        stage.show();
    }

    /**
     * The switches the scene to the game scene
     * */
    public void enterGame() {
        window.setScene(this.buildGameScene());
    }

    /**
     * Builds the scene for the menu
     * @return The menu scene
     * */
    public Scene buildMenuScene(){
        //The basic layout
        HBox layout = new HBox();

        //The start button
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> enterGame());

        //The exit button
        Button exitButton = new Button("Quit");
        exitButton.setOnAction(e -> Platform.exit());

        //Adds the buttons to the layout and returns the full scene
        layout.getChildren().addAll(startButton, exitButton);
        Scene scene = new Scene(layout, 300, 250);
        return scene;
    }

    /**
     * Initializes the cell grid
     * @return The game scene
     * */
    public Scene buildGameScene(){
        initCellGrid();

        //Creates the bottom menu buttons
        HBox bottomMenu = new HBox();
        Button menuButton = new Button("Main menu");
        Button advanceGenerationButton = new Button("Advance 1 Generation");
        Button advanceMultipleGenerationsButton = new Button("Advance N generations");
        TextField generationsInput = new TextField();
        Button clearButton = new Button("Clear the board");

        //Adds the actions to the buttons
        menuButton.setOnAction(e -> window.setScene(buildMenuScene()));
        generationsInput.setText("10");

        advanceGenerationButton.setOnAction(e -> {
            BoardHandler.advanceGenerations(1);
            updateCellUI();
        });
        advanceMultipleGenerationsButton.setOnAction(e -> {
            try{
                BoardHandler.advanceGenerations(Integer.parseInt(generationsInput.getText()));
                updateCellUI();
            } catch(NumberFormatException exception){

            }
        });
        clearButton.setOnAction(e ->{
            BoardHandler.currentBoard.fillBlankBoard(BoardHandler.currentBoard.getWidth(), BoardHandler.currentBoard.getHeight());
            updateCellUI();
        });

        //Completes and returns the scene
        bottomMenu.getChildren().addAll(menuButton, advanceGenerationButton, advanceMultipleGenerationsButton, generationsInput, clearButton);
        BorderPane layout = new BorderPane();
        layout.setCenter(cellGrid);
        layout.setBottom(bottomMenu);
        return new Scene(layout, BoardHandler.currentBoard.getWidth()*cellUISize, BoardHandler.currentBoard.getHeight()*cellUISize);
    }

    /**
     * Initializes the game board
     * */
    public static void initGame() {
        BoardHandler.currentBoard.fillBlankBoard(50, 50);
        cellUISize = ((int)(((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())*0.75D))/50;
//        BoardHandler.currentBoard.setCell(0, 0, true);
//        BoardHandler.currentBoard.setCell(1, 0, true);
//        BoardHandler.currentBoard.setCell(0, 1, true);
//        BoardHandler.currentBoard.setCell(1, 1, true);
    }

    /**
     * Initializes the cell grid
     * Sets the cell grid to a pane and fills with newly generated cell UI components
     * Adds the mouse click functionality to the cell grid
     * */
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
        //Adds the action to the cell grid
        cellGrid.setOnMouseClicked(e -> {
            int x = (int) e.getX() / cellUISize;
            int y = (int) e.getY() / cellUISize;
            BoardHandler.currentBoard.setCell(x, y, !BoardHandler.currentBoard.getCell(x, y));
            updateCellUI();
        });
    }

    /**
     * Updates the cell UI components to match the current board
     * */
    public static void updateCellUI() {
        for (int i = 0; i < cellGrid.getChildren().size(); i++) {
            CellUIComponent cellUI = (CellUIComponent) cellGrid.getChildren().get(i);
            cellUI.updateCell(Boolean.TRUE.equals(BoardHandler.currentBoard.getCell(cellUI.getCellX(), cellUI.getCellY())));
        }
    }
}
