package com.thechiefpotatopeeler.schoolproject2023.io.gui;

import com.thechiefpotatopeeler.schoolproject2023.board.Board;
import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

/**
 * The class which handles the GUI (currently running the application when using GUI as well)
 * */
@SuppressWarnings("unnamed module")
public class UIApplication extends Application {

    public Stage window; // The window which the application runs in

    public static Boolean colourBlindMode = false; // Whether or not colour blind mode is enabled
    public static int cellUISize = 30;// The size of the cell UI components

    public static Pane cellGrid;// The grid of cells

    public static Thread UIUpdateHandler;

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
        this.window.setOnCloseRequest(e -> exitProcedures());
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
        HBox buttons = new HBox();
        HBox textBoxes = new HBox();

        textBoxes.setPadding(new Insets(3.0D));
        textBoxes.setSpacing(3.0D);

        //The exit button
        Button exitButton = new Button("Quit");
        exitButton.setOnAction(e -> exitProcedures());

        //The board dimensions
        Text xPrompt = new Text();
        Text yPrompt = new Text();
        TextField xInput = new TextField();
        TextField yInput = new TextField();
        xPrompt.setText("x: ");
        yPrompt.setText("y: ");
        xPrompt.setScaleX(1.5D);
        xPrompt.setScaleY(1.5D);
        yPrompt.setScaleX(1.5D);
        yPrompt.setScaleY(1.5D);
        xInput.setText("50");
        xInput.setMaxWidth(50);
        yInput.setText("50");
        yInput.setMaxWidth(50);
        CheckBox colourBlindModeCheckBox = new CheckBox("Colour blind mode");

        //The start button
        Button startButton = new Button("Start");
            startButton.setOnAction(e ->{
                try{
                    BoardHandler.setSize(Integer.parseInt(xInput.getText()),Integer.parseInt(yInput.getText()));
                    colourBlindMode = colourBlindModeCheckBox.isSelected();
                    enterGame();
                } catch(NumberFormatException ignored){}
            });

        //Adds the buttons to the layout and returns the full scene
        buttons.getChildren().addAll(startButton, exitButton, colourBlindModeCheckBox);
        textBoxes.getChildren().addAll(xPrompt,xInput,yPrompt,yInput);
        //textBoxes.setPadding();
        BorderPane layout = new BorderPane();
        layout.setTop(buttons);
        layout.setCenter(textBoxes);
        return new Scene(layout, 300, 250);
    }

    /**
     * Initializes the cell grid
     * @return The game scene
     * */


    public Scene buildGameScene(){
        initGame();
        initCellGrid();
        UIUpdateHandler = new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ignored) {}
                Platform.runLater(UIApplication::updateCellUI);
            }
        });
        UIUpdateHandler.start();

        //Creates the bottom menu buttons
        HBox bottomMenu = new HBox();
        Button menuButton = new Button("Main menu");
        Button advanceGenerationButton = new Button("Advance 1 Generation");
        Button advanceMultipleGenerationsButton = new Button("Advance N generations");
        TextField generationsInput = new TextField();
        Button clearButton = new Button("Clear the board");
        Label displayGenerationsLabel = new Label("Display generations:");
        //Adds the actions to the buttons
        menuButton.setOnAction(e -> window.setScene(buildMenuScene()));
        generationsInput.setText("10");

        advanceGenerationButton.setOnAction(e -> {
            BoardHandler.advanceGenerations(1,()->{});
            //updateCellUI();
        });
        advanceMultipleGenerationsButton.setOnAction(e -> {
            try{
                BoardHandler.advanceGenerations(Integer.parseInt(generationsInput.getText()), () -> {});
                //updateCellUI();
            } catch(NumberFormatException ignored){}
        });
        clearButton.setOnAction(e ->{
            BoardHandler.currentBoard.fillBlankBoard(BoardHandler.currentBoard.getWidth(), BoardHandler.currentBoard.getHeight());
            //updateCellUI();
        });

        //Completes and returns the scene
        bottomMenu.getChildren().addAll(menuButton, advanceGenerationButton, advanceMultipleGenerationsButton, generationsInput, clearButton);
        BorderPane layout = new BorderPane();
        layout.setCenter(cellGrid);
        layout.setBottom(bottomMenu);
        return new Scene(layout, BoardHandler.getWidth()*cellUISize, BoardHandler.getHeight()*cellUISize);
    }

    /**
     * Initializes the game board
     * */
    public static void initGame() {
        BoardHandler.currentBoard.fillBlankBoard(BoardHandler.getWidth(), BoardHandler.getHeight());
        cellUISize = ((int)(((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())*0.75D))/50;
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
            //updateCellUI();
        });
        //Main.logger.info("Cell grid initialized");
    }

    /**
     * Updates the cell UI components to match the current board
     * */
    public static void updateCellUI() {
        for (int i = 0; i < cellGrid.getChildren().size(); i++) {
            CellUIComponent cellUI = (CellUIComponent) cellGrid.getChildren().get(i);
            cellUI.updateCell(Boolean.TRUE.equals(BoardHandler.currentBoard.getCell(cellUI.getCellX(), cellUI.getCellY())));
        }
        //TextUI.printBoard();
    }

    public static void exitProcedures(){
        if(UIUpdateHandler!=null) UIUpdateHandler.interrupt();
        Platform.exit();
        System.exit(0);
    }
}
