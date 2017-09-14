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
        Stage initialise = Gui.createStartWindow();

        Gui.buttons.get("createStartWindowonPlayerButton").setOnAction(event -> {
            initialise.close();
            Game game = new Game(1);
            Gui.getPrimaryStage().setTitle("Snake Game");
            Scene scene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
            Gui.chooseTheme(game);
            Gui.getPrimaryStage().setScene(scene);
            Gui.getPrimaryStage().show();
            game.start();
        });
    }
}
