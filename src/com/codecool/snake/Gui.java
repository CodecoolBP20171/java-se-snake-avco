package com.codecool.snake;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui {

    public static void popUpWindow(Stage primaryStage) {
        Globals.gameLoop.stop();

        Stage dialog = new Stage();

        Button restartButton = new Button();
        restartButton.setText("Restart");
        restartButton.setOnAction(event -> restartGame(primaryStage, dialog));

        Button continueButton = new Button();
        continueButton.setText("Continue");
        continueButton.setOnAction(event -> continueTheGame(dialog));

        VBox dialogVbox = new VBox(20);
        dialogVbox.setStyle("-fx-background-color:#fffa77;");
        Scene dialogScene = new Scene(dialogVbox, 300, 200);

        Text text = new Text();
        text.setText("Game paused");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        text.setFill(Color.BLACK);

        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(text);
        dialogVbox.getChildren().add(restartButton);
        dialogVbox.getChildren().add(continueButton);

        dialog.initOwner(primaryStage);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> Globals.gameLoop.start());
    }

    public static void gameOverWindow(Stage primaryStage, int score) {
        Globals.gameLoop.stop();

        Stage gameOverStage = new Stage();

        Button restartButton = new Button();
        restartButton.setText("Restart");
        restartButton.setOnAction(event -> restartGame(primaryStage, gameOverStage));

        Button exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction(event -> Platform.exit());

        VBox gameOverBox = new VBox(20);
        gameOverBox.setStyle("-fx-background-color:#fffa77;");
        Scene gameOverScene = new Scene(gameOverBox, 300, 200);

        Text text = new Text();
        text.setText("GAME OVER!\n" + "Your score: " + score);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        text.setFill(Color.BLACK);

        gameOverBox.setAlignment(Pos.CENTER);
        gameOverBox.getChildren().add(text);
        gameOverBox.getChildren().add(restartButton);
        gameOverBox.getChildren().add(exitButton);

        gameOverStage.initOwner(primaryStage);
        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
        gameOverStage.setOnCloseRequest(event -> Platform.exit());
    }

    private static void continueTheGame(Stage dialog) {
        dialog.close();
        Globals.gameLoop.start();
    }

    private static void restartGame(Stage primaryStage, Stage dialog) {
        new Main().start(primaryStage);
        continueTheGame(dialog);
        Globals.clearGameObjects();
    }
}

