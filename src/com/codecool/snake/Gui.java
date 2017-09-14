package com.codecool.snake;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.Global;

import java.util.HashMap;

public class Gui {
    private static Stage primaryStage;
    public static HashMap<String,Button> buttons = new HashMap<>();


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Gui.primaryStage = primaryStage;
    }
    public static Stage createStartWindow(){
        Stage initialise = new Stage();
        GridPane root = new GridPane();
        Button onePlayerButton = new Button("1 Player");
        Button twoPlayerButton = new Button("2 Players");
        initialise.setTitle("Start new game");

        ImageView pic = new ImageView();
        ImageView pic1 = new ImageView();
        Line corridor = new Line(200, 0, 200, 200);

        pic.setFitHeight(130);
        pic.setFitWidth(130);
        pic1.setFitHeight(130);
        pic1.setFitWidth(130);
        pic.setImage(Globals.snakeProf);
        pic1.setImage(Globals.snakeDoubleProf);

        root.add(pic, 0, 0, 1, 1);
        root.add(onePlayerButton, 0, 1 ,1,1);
        root.add(pic1, 3, 0, 1, 1);
        root.add(twoPlayerButton, 3, 1, 1, 1);
        root.add(corridor, 2, 0, 1, 1);
        root.setHgap(30);
        root.setVgap(70);
        buttons.put("createStartWindowonPlayerButton",onePlayerButton);
        buttons.put("createStartWindowtwoPlayerButton",twoPlayerButton);
        Scene preScene = new Scene(root, 350, 400);
        initialise.setScene(preScene);
        initialise.show();

        return initialise;
    }


    public static void createHealthBar(ProgressBar progressBar, int snakeHealth) {
        progressBar.setProgress((double) snakeHealth /100);
    }

    public static void popUpWindow() {
        Stage dialog = new Stage();
        VBox dialogVbox = new VBox(20);
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        createRestartButton(dialog, dialogVbox);
        createContinueButton(dialog, dialogVbox);
        dialog.initOwner(Gui.primaryStage);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> Globals.gameLoop.start());
    }

    public static void chooseTheme(Game game) {
        Rectangle test = new Rectangle(Globals.WINDOW_WIDTH , Globals.WINDOW_HEIGHT );
        test.setStroke(Color.YELLOWGREEN);
        test.setFill(Color.LIGHTSEAGREEN);
        game.getChildren().add(test);
        test.setStrokeWidth(20);

        Line gate = new Line(5, Globals.WINDOW_HEIGHT / 2 - 50, 5, Globals.WINDOW_HEIGHT / 2 + 50);
        gate.setFill(Color.RED);
        gate.setStrokeWidth(10);
        game.getChildren().add(gate);

        Line gate2 = new Line(Globals.WINDOW_WIDTH -5 , Globals.WINDOW_HEIGHT / 2 - 50, Globals.WINDOW_WIDTH - 5, Globals.WINDOW_HEIGHT / 2 + 50);
        gate2.setFill(Color.RED);
        gate2.setStrokeWidth(10);
        game.getChildren().add(gate2);
    }

    public static void gameOverWindow(int length) {
        Globals.gameLoop.stop();
        Stage gameOverStage = new Stage();

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

        createRestartButton(gameOverStage, gameOverBox);
        createExitButton(gameOverBox);

        gameOverStage.initOwner(Gui.primaryStage);
        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
        gameOverStage.setOnCloseRequest(event -> Platform.exit());
    }

    private static void createContinueButton(Stage stage, VBox vBox) {
        Button continueButton = new Button();
        continueButton.setText("Continue");
        Globals.gameLoop.stop();
        continueButton.setOnAction(event -> {
            continueTheGame(stage);
        });
        vBox.getChildren().add(continueButton);
    }

    private static void createRestartButton(Stage stage, VBox dialogVbox) {
        Button restartButton = new Button();
        restartButton.setText("Restart");
        restartButton.setOnAction(event -> restartGame(stage));
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

    private static void restartGame( Stage dialog) {
        new Main().start(Gui.primaryStage);
        continueTheGame(dialog);
        Globals.clearGameObjects();
    }

    public static void createScoreBar(Game game) {
        Label score = new Label();
        score.textProperty().bind(Globals.players.get(0).getScore());
        score.setLayoutX(180);
        score.setLayoutY(20);
        score.setFont(new Font(24));
        game.getChildren().add(score);

        if (Globals.players.size() > 1) {
            Label score2 = new Label();
            score2.textProperty().bind(Globals.players.get(1).getScore());
            score2.setLayoutX(Globals.WINDOW_WIDTH - 280);
            score2.setLayoutY(20);
            score2.setFont(new Font(24));
            game.getChildren().add(score2);
        }
    }
}

