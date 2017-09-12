package com.codecool.snake;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Game game = new Game();

        primaryStage.setTitle("Snake Game");
        Scene scene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

        game.start();
    }

    public static void restart(Stage primaryStage) {
        Button restartButton = new Button();
        restartButton.setText("Restart");
        Button continueButton = new Button();
        continueButton.setText("Continue");
        final Stage dialog = new Stage();
        Globals.gameLoop.stop();
        continueButton.setOnAction(event -> {
            dialog.close();
            Globals.gameLoop.start();
        });
        restartButton.setOnAction(event -> {
            new Main().start(primaryStage);
            dialog.close();
            Globals.gameLoop.start();
            Globals.clearGameObjects();
        });
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(restartButton);
        dialogVbox.getChildren().add(continueButton);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> Globals.gameLoop.start());
    }
}
