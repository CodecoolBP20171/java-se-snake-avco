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
        Button restartButton = new Button();
        Button continueButton = new Button();
        restartButton.setText("Restart");
        continueButton.setText("Continue");
        Stage dialog = new Stage();
        Globals.gameLoop.stop();
        continueButton.setOnAction(event -> {
            continueTheGame(dialog);
        });
        restartButton.setOnAction(event -> {
            restartGame(primaryStage, dialog);
        });
        VBox dialogVbox = new VBox(20);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);

        dialogVbox.getChildren().add(restartButton);
        dialogVbox.getChildren().add(continueButton);

        dialog.initOwner(primaryStage);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> Globals.gameLoop.start());
    }

    public static void gameOverWindow(Stage primaryStage, int length) {
        Button restartButton = new Button();
        Button exitButton = new Button();
        restartButton.setText("Restart");
        exitButton.setText("Exit");

        Stage gameOverStage = new Stage();
        Globals.gameLoop.stop();
        exitButton.setOnAction(event -> Platform.exit());
        restartButton.setOnAction(event -> restartGame(primaryStage, gameOverStage));

        VBox gameOverBox = new VBox(20);
        gameOverBox.setStyle("-fx-background-color:#fff;");
        Scene gameOverScene = new Scene(gameOverBox, 300, 200);

        Text text = new Text();
        text.setText("GAME OVER!\n" + "Your score1: " + length);
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        text.setFill(Color.BLACK);
        pane.getChildren().add(text);
        gameOverBox.getChildren().add(pane);

        gameOverBox.setAlignment(Pos.CENTER);
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

