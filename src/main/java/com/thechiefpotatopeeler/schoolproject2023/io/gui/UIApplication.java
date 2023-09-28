package com.thechiefpotatopeeler.schoolproject2023.io.gui;

import com.thechiefpotatopeeler.schoolproject2023.board.Board;
import com.thechiefpotatopeeler.schoolproject2023.board.BoardHandler;
import com.thechiefpotatopeeler.schoolproject2023.io.JSONHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author Thomas Jackson
 *
 * The class which handles the GUI (currently running the application when using GUI as well)
 * */
@SuppressWarnings("unnamed module")
public class UIApplication extends Application {

    //Labels and other constants
    private static final String MAIN_MENU_LABEL = "Main menu";
    private static final String QUIT_LABEL = "Quit";
    private static final String COLOURBLIND_LABEL = "Colour blind mode";
    private static final String GAME_TITLE = "Conway's Game of Life";
    private static final String START_LABEL = "Start";
    private static final int UPDATE_HANDLER_SLEEP_TIME = 5;
    private static final String ADVANCE_ONE_LABEL = "Advance 1 generation";
    private static final String ADVANCE_MULTIPLE_LABEL = "Advance N generations";
    private static final String SAVE_LABEL = "Save board";
    private static final String LOAD_LABEL = "Load board";
    private static final String DEFAULT_JSON_FILE_NAME = "board.json";
    private static final String BOARD_LOAD_ERROR_HEADER = "Loading board";
    private static final String JSON_FILE_FILTER_TAG = "The file you selected is not a valid board file";
    private static final String JSON_FILE_FILTER = "*.json";

    //Functional variables
    public static Stage window;
    public static Boolean colourBlindMode = false;
    public static int cellUISize = 30;
    public static Pane cellGrid;

    public static Thread UIUpdateHandler;
    private static final String CLEAR_LABEl = "Clear the board";
    public static FileChooser fileChooser = new FileChooser();

    /**
     * Enum for the state of the UI
     * */
    public enum UIState{
        MENU(0){
            @Override
            public Scene getSceneFromState() {
                return buildMenuScene();
            }
        },
        GAME(1){
            @Override
            public Scene getSceneFromState() {
                return buildGameScene();
            }
        };

        UIState(int state) {

        }

        public Scene getSceneFromState(){
            return null;
        }
    }

    public static UIState uiState = UIState.MENU;

    /**
     * The method which runs when the application is started
     * @param stage The stage which the application runs in
     * */
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        stage.setTitle(GAME_TITLE);
        uiState = UIState.MENU;
        stage.setScene(uiState.getSceneFromState());
        stage.show();
        window.setOnCloseRequest(e -> exitProcedures());
    }
    /**
     * Builds the scene for the menu
     * @return The menu scene
     * */
    public static Scene buildMenuScene(){
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
        startButton.setTooltip(new Tooltip(("Boards over 100x100 cells may take a while to load or be too small too see")));
        startButton.tooltipProperty().get().setShowDelay(javafx.util.Duration.millis(5));
        startButton.setOnAction(e ->{
            try{
                BoardHandler.setSize(Integer.parseInt(xInput.getText()),Integer.parseInt(yInput.getText()));
                colourBlindMode = colourBlindModeCheckBox.isSelected();
                UIApplication.uiState = UIState.GAME;
                window.setScene(UIApplication.uiState.getSceneFromState());
            } catch(NumberFormatException ignored){}
        });

        //Adds the buttons to the layout and returns the full scene
        buttons.getChildren().addAll(startButton, exitButton, colourBlindModeCheckBox);
        textBoxes.getChildren().addAll(xPrompt,xInput,yPrompt,yInput);
        //textBoxes.setPadding();
        BorderPane layout = new BorderPane();
        layout.setTop(buttons);
        layout.setCenter(textBoxes);
        return new Scene(layout, 250, 100);
    }

    /**
     * Initializes the cell grid
     * @return The game scene
     * */
    public static Scene buildGameScene(){
        initGame();
        initCellGrid();
        UIUpdateHandler = new Thread(() -> {
            while(UIApplication.uiState.equals(UIState.GAME)){
                try {
                    Thread.sleep(UPDATE_HANDLER_SLEEP_TIME);
                } catch (InterruptedException ignored) {}
                Platform.runLater(UIApplication::updateCellUI);
            }
        });
        UIUpdateHandler.start();

        //Creates the menu components
        HBox bottomMenu = new HBox();
        Button menuButton = new Button(MAIN_MENU_LABEL);
        Button advanceGenerationButton = new Button(ADVANCE_ONE_LABEL);
        Button advanceMultipleGenerationsButton = new Button(ADVANCE_MULTIPLE_LABEL);
        Button saveBoardButton = new Button(SAVE_LABEL);
        Button loadBoardButton = new Button(LOAD_LABEL);
        TextField generationsInput = new TextField();
        Button clearButton = new Button(CLEAR_LABEl);
        //Adds the actions to the buttons
        menuButton.setOnAction(e ->{
            UIApplication.uiState = UIState.MENU;
            window.setScene(uiState.getSceneFromState());
        });
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

        saveBoardButton.setOnAction(e ->{
            fileChooser.setTitle(SAVE_LABEL);
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(JSON_FILE_FILTER_TAG, JSON_FILE_FILTER));
            fileChooser.setInitialFileName(DEFAULT_JSON_FILE_NAME);
            File file = fileChooser.showSaveDialog(window);
            try {
                FileUtils.writeStringToFile(file, new JSONHandler().exportBoard(BoardHandler.currentBoard).toString(), StandardCharsets.UTF_8);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        loadBoardButton.setOnAction(e ->{
            //Sets up to load the board
            fileChooser.setTitle(LOAD_LABEL);
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(JSON_FILE_FILTER_TAG, JSON_FILE_FILTER));
            File file = fileChooser.showOpenDialog(window);
            try {
                Board board = new JSONHandler().loadBoard(FileUtils.readFileToString(file, StandardCharsets.UTF_8));
                if(board.getWidth()!=BoardHandler.currentBoard.getWidth()||board.getHeight()!=BoardHandler.currentBoard.getHeight()){
                    showErrorPopup(BOARD_LOAD_ERROR_HEADER, "The file you selected was not the same size as your current board, please change the size.");
                    return;
                }
                BoardHandler.currentBoard = board;
            } catch (IOException | ParseException | ClassCastException ex) {
                if(ex instanceof ClassCastException){//Handles the case where the file is not a valid board file
                    showErrorPopup(BOARD_LOAD_ERROR_HEADER,"The file you selected is not a valid board file");
                } else if(ex instanceof IOException){//Handles the case where the file cannot be read
                    showErrorPopup(BOARD_LOAD_ERROR_HEADER,"The file you selected could not be read");
                } else {//Handles the case where the file is not valid JSON
                    showErrorPopup(BOARD_LOAD_ERROR_HEADER,"The file you selected contains invalid JSON");
                }
                throw new RuntimeException(ex);
            }
        });

        //Completes and returns the scene
        bottomMenu.getChildren().addAll(menuButton, advanceGenerationButton, advanceMultipleGenerationsButton, generationsInput, clearButton, saveBoardButton, loadBoardButton);
        BorderPane layout = new BorderPane();
        layout.setCenter(cellGrid);
        layout.setBottom(bottomMenu);
        return new Scene(layout, BoardHandler.getWidth()*cellUISize, BoardHandler.getHeight()*cellUISize);
    }

    /**
     * Builds an error window
     * @param actionFailed The action that failed, the string is appended onto "Error"
     *                     e.g. "Error saving board"
     * @param content The content of the error message
     *                e.g. "The file you selected could not be read"
     * */
    public static void showErrorPopup(String actionFailed, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(String.format("Error %s", actionFailed));
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Fills a blank board for the game to use
     * */
    public static void initGame() {
        BoardHandler.currentBoard.fillBlankBoard(BoardHandler.getWidth(), BoardHandler.getHeight());
    }

    /**
     * Initializes the cell grid
     * Sets the cell grid to a pane and fills with newly generated cell UI components
     * Adds the mouse click functionality to the cell grid
     * */
    public static void initCellGrid(){
        cellUISize = ((int)(((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight())*0.75D))/BoardHandler.currentBoard.getWidth();
        cellGrid = new Pane();
        Board board = BoardHandler.currentBoard;
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                CellUIComponent cellUI = new CellUIComponent(j,i);
                cellGrid.getChildren().add(cellUI);
            }
        }
        EventHandler<MouseEvent> mouseHandler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) event.getX() / cellUISize;
                int y = (int) event.getY() / cellUISize;
                BoardHandler.currentBoard.setCell(x, y, Boolean.FALSE.equals(BoardHandler.currentBoard.getCell(x, y)));
            }
        };
        //Adds the action to the cell grid
        cellGrid.setOnMouseClicked(mouseHandler);
        cellGrid.setOnMouseDragged(mouseHandler);
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
