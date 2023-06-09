package com.thechiefpotatopeeler.schoolproject2023.display.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIApplication extends Application {

    public Stage window;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        stage.setTitle("Conway's Game of Life");
        stage.setScene(this.buildMenuScene());
        stage.show();
    }

    public void enterGame() {
        window.setScene(this.buildGameScene());
    }

    public Scene buildMenuScene(){
        VBox layout = new VBox();

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> enterGame());

        Button exitButton = new Button("Quit");
        exitButton.setOnAction(e -> Platform.exit());

        layout.getChildren().addAll(startButton, exitButton);

        Scene scene = new Scene(layout, 300, 250);
        return scene;
    }

    public Scene buildGameScene(){
        return new Scene(new VBox(), 300, 250);
    }
}
