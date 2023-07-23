package com.thechiefpotatopeeler.schoolproject2023.io.gui;

import com.thechiefpotatopeeler.schoolproject2023.board.Board;
import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

/**
 * @author Thomas Jackson
 *
 * The class which handles the GUI (currently running the application when using GUI as well)
 * */
@SuppressWarnings("unnamed module")
public class UIApplication extends Application {

    private static final String MAIN_MENU_LABEL = "Main menu";
    private static final String QUIT_LABEL = "Quit";
    private static final String COLOURBLIND_LABEL = "Colour blind mode";
    private static final String GAME_TITLE = "Conway's Game of Life";
    private static final String START_LABEL = "Start";
    private static final int UPDATE_HANDLER_SLEEP_TIME = 5;
    private static final String ADVANCE_ONE_LABEL = "Advance 1 generation";
    private static final String ADVANCE_MULTIPLE_LABEL = "Advance N generations";

    public Stage window; // The window which the application runs in

    public static Boolean colourBlindMode = false; // Whether or not colour blind mode is enabled
    public static int cellUISize = 30;// The size of the cell UI components

    public static Pane cellGrid;// The grid of cells

    public static Thread UIUpdateHandler;
    private static final String CLEAR_LABEl = "Clear the board";

    /**
     * The method which runs when the application is started
     * @param stage The stage which the application runs in
     * */
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        stage.setTitle(GAME_TITLE);
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
        Button exitButton = new Button(QUIT_LABEL);
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
        CheckBox colourBlindModeCheckBox = new CheckBox(COLOURBLIND_LABEL);

        //The start button
        Button startButton = new Button(START_LABEL);
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
                    Thread.sleep(UPDATE_HANDLER_SLEEP_TIME);
                } catch (InterruptedException ignored) {}
                Platform.runLater(UIApplication::updateCellUI);
            }
        });
        UIUpdateHandler.start();

        //Creates the bottom menu buttons
        HBox bottomMenu = new HBox();
        Button menuButton = new Button(MAIN_MENU_LABEL);
        Button advanceGenerationButton = new Button(ADVANCE_ONE_LABEL);
        Button advanceMultipleGenerationsButton = new Button(ADVANCE_MULTIPLE_LABEL);
        TextField generationsInput = new TextField();
        Button clearButton = new Button(CLEAR_LABEl);
        //Adds the actions to the buttons
        menuButton.setOnAction(e -> window.setScene(buildMenuScene()));
        generationsInput.setText("10");

        //Adds functionality to buttons
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

    /**
     * Procedures for closing the program
     * */
    public static void exitProcedures(){
        if(UIUpdateHandler!=null) UIUpdateHandler.interrupt();
        Platform.exit();
        System.exit(0);
    }
}
