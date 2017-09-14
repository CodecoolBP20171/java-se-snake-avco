package com.codecool.snake;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gui {
    public static void setPrimaryStage(Stage primaryStage) {
        Gui.primaryStage = primaryStage;
    }

    private static Stage primaryStage;
    private static List<Image> startPictures = new ArrayList<>();
    public static HashMap<String,Button> buttons = new HashMap<>();
    public static int numberOfPlayers = 0;

    public static void addStartPictures(){
        startPictures.add( new Image("1player.png"));
        startPictures.add(new Image("2player.png"));
        startPictures.add( new Image("3player.png"));
        startPictures.add(new Image("4player.png"));
    }


    public static void createHealthBar(ProgressBar progressBar, int snakeHealth) {
        progressBar.setProgress((double) snakeHealth / 100);
    }

    public static Stage createStartWindow(int number){
        Stage initialise = new Stage();
        Button leftButton = new Button("\uD83E\uDC80");
        Button rightButton = new Button("\uD83E\uDC82");
        Button startButton = new Button("Start");
        initialise.setTitle("Start new game");
        VBox vBox = new VBox();
        HBox playerNumberBox = new HBox(3);
        HBox start = new HBox(1);
        ImageView imageView = new ImageView();
        playerNumberBox.setPadding(new Insets(20));
        
        leftButton.setPrefSize(100,20);
        rightButton.setPrefSize(100,20);
        playerNumberBox.setAlignment(Pos.CENTER);
        playerNumberBox.getChildren().addAll(leftButton);
        playerNumberBox.getChildren().add(imageView);
        playerNumberBox.getChildren().addAll(rightButton);
        imageView.setFitWidth(250);
        imageView.setFitHeight(250);
        imageView.setImage(startPictures.get(number));
        playerNumberBox.setSpacing(30);
        start.setPrefSize(400,20);
        start.getChildren().add(startButton);
        start.setAlignment(Pos.BOTTOM_CENTER);
        start.setPadding(new Insets(20));
        vBox.getChildren().addAll(playerNumberBox,start);
        vBox.setStyle("-fx-background-color: deepskyblue;");
        buttons.put("startButton",startButton);
        buttons.put("leftButton",leftButton);
        buttons.put("rightButton",rightButton);
        Scene preScene = new Scene(vBox, 600 , 350);
        initialise.setScene(preScene);

        buttons.put("leftButton",leftButton);
        buttons.put("rightButton",rightButton);
        leftButton.setOnAction(event -> {
            if (numberOfPlayers -1 > -1){

                numberOfPlayers = numberOfPlayers-1;
                imageView.setImage(startPictures.get(numberOfPlayers));

            }
        });

        rightButton.setOnAction(event -> {
            if (numberOfPlayers + 1 < startPictures.size()){

                numberOfPlayers = numberOfPlayers+1;
                imageView.setImage(startPictures.get(numberOfPlayers));


            }
        });

        initialise.show();

        return initialise;
    }


    public static void popUpWindow() {

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

        createRestartButton(dialog, dialogVbox);
        createContinueButton(dialog, dialogVbox);
        dialog.initOwner(Gui.primaryStage);

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

    public static void gameOverWindow() {

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
        continueButton.setOnAction(event -> continueTheGame(stage));
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
        Globals.gameObjects.clear();
        primaryStage.close();
        new Main().start(new Stage());
        dialog.close();
    }

    public static void createScoreBar(Game game, int players) {
        Label score = new Label();
        for (int i = 0; i < players; i++) {
            Globals.players.get(i).setScore();
            score.textProperty().bind(Globals.players.get(i).getScore());
            if (i == 0 || i == 2) { score.setLayoutX(50); }
            if (i == 1 || i == 3) { score.setLayoutX(Globals.WINDOW_WIDTH - 150); }
            if (i < 2) {
                score.setLayoutY(40);
            } else {score.setLayoutY(Globals.WINDOW_HEIGHT - 80);
            }
            score.setFont(new Font(24));
            game.getChildren().add(score);
            score = new Label();
        }
    }
}

