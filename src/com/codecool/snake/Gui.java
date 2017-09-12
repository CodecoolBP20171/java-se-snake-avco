package com.codecool.snake;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Gui {

        public static void popUpWindow(Stage primaryStage){
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

