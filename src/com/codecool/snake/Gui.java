package com.codecool.snake;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

public class Gui {

    public static void createHealthBar(ProgressBar progressBar, int snakeHealth) {
        progressBar.setProgress((double) snakeHealth / 100);


    }

    public static void popUpWindow(Stage primaryStage) {
        Globals.gameLoop.stop();

        Stage dialog = new Stage();

        VBox dialogVbox = new VBox(20);
//        dialogVbox.setStyle("-fx-background-color:#fffa77;");
        Scene dialogScene = new Scene(dialogVbox, 300, 200);


        Text text = new Text();
        text.setText("Game paused");
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        text.setFill(Color.BLACK);

        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(text);

        createRestartButton(primaryStage, dialog, dialogVbox);
        createContinueButton(dialog, dialogVbox);

        dialog.initOwner(primaryStage);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> Globals.gameLoop.start());
    }

    public static void chooseTheme(Game game) {
        Rectangle test = new Rectangle(Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
        test.setStroke(Color.YELLOWGREEN);
        test.setFill(Color.LIGHTBLUE);

        game.getChildren().add(test);
        test.setStrokeWidth(20);

        Line gate = new Line(5, Globals.WINDOW_HEIGHT / 2 - 50, 5, Globals.WINDOW_HEIGHT / 2 + 50);
        gate.setFill(Color.RED);
        gate.setStrokeWidth(10);
        game.getChildren().add(gate);

        Line gate2 = new Line(Globals.WINDOW_WIDTH - 5, Globals.WINDOW_HEIGHT / 2 - 50, Globals.WINDOW_WIDTH - 5, Globals.WINDOW_HEIGHT / 2 + 50);
        gate2.setFill(Color.RED);
        gate2.setStrokeWidth(10);
        game.getChildren().add(gate2);
    }

    public static void gameOverWindow(Stage primaryStage) {
        Globals.gameLoop.stop();
        Stage gameOverStage = new Stage();

        VBox gameOverBox = new VBox(20);
        gameOverBox.setStyle("-fx-background-color:#fffa77;");
        Scene gameOverScene = new Scene(gameOverBox, 300, 300);

        Text text = new Text();
        StringBuilder scores = new StringBuilder();
        scores.append("\nScores:\n\n");
        for (Map.Entry<String, Integer> entry : Globals.scoreList.entrySet()) {
            String snakeColor = entry.getKey();
            Integer score = entry.getValue();
            scores.append(snakeColor.toUpperCase())
                    .append(": ").append(score)
                    .append(System.lineSeparator());
        }

        text.setText("GAME OVER!\n" + scores);
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);

        text.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        text.setFill(Color.BLACK);

        gameOverBox.setAlignment(Pos.CENTER);
        gameOverBox.getChildren().add(text);

        createRestartButton(primaryStage, gameOverStage, gameOverBox);
        createExitButton(gameOverBox);

        gameOverStage.initOwner(primaryStage);
        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
        gameOverStage.setOnCloseRequest(event -> Platform.exit());
    }

    private static void createContinueButton(Stage stage, VBox vBox) {
        Button continueButton = new Button();
        continueButton.setText("Continue");
        Globals.gameLoop.stop();
        continueButton.setOnAction(event -> continueTheGame(stage));
        vBox.getChildren().add(continueButton);
    }

    private static void createRestartButton(Stage primaryStage, Stage stage, VBox dialogVbox) {
        Button restartButton = new Button();
        restartButton.setText("Restart");
        restartButton.setOnAction(event -> restartGame(primaryStage, stage));
        dialogVbox.getChildren().add(restartButton);
    }

    private static void createExitButton(VBox gameOverBox) {
        Button exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction(event -> Platform.exit());
        gameOverBox.getChildren().add(exitButton);
    }

    private static void continueTheGame(Stage dialog) {
        dialog.close();
        Globals.gameLoop.start();
    }

    private static void restartGame(Stage primaryStage, Stage dialog) {
        dialog.close();
        new Main().start(primaryStage);
    }

}

