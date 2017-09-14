package com.codecool.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Gui.setPrimaryStage(primaryStage);
        Gui.addStartPictures();
        Stage initialise = Gui.createStartWindow(0);
        Gui.buttons.get("createStartWindowonPlayerButton").setOnAction(event -> {
            Gui.numberOfPlayers = 1;
            Gui.createStartWindow(1);
            initialise.close();

        });
    }
}
