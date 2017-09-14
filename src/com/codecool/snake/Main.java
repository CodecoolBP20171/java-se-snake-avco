package com.codecool.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        Main gameUI = new Main();

        gameUI.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        Gui.addStartPictures();
        Stage initialise = Gui.createStartWindow(0);
        Gui.setPrimaryStage(primaryStage);

        Gui.buttons.get("startButton").setOnAction(event -> {
            Game game = new Game(Gui.numberOfPlayers+1);
            Scene scene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
            Gui.chooseTheme(game);
            primaryStage.setTitle("Snake Game");
            primaryStage.setScene(scene);
            primaryStage.show();
            initialise.close();
            game.start();

        });
    }
}
