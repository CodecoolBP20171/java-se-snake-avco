package com.codecool.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static Game GAME;

    public static Game getGAME() {
        return GAME;
    }
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Game game = new Game();
        this.GAME = game;
        primaryStage.setTitle("Snake Game");
        Scene scene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

        Gui.chooseTheme(game);
        primaryStage.setScene(scene);
        primaryStage.show();

        game.start();
    }

}
