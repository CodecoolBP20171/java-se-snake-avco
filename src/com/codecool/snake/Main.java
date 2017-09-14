package com.codecool.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
        Stage initialise = new Stage();
        GridPane root = new GridPane();
        Button button1 = new Button("1 Player");
        Button button2 = new Button("2 Players");
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
        root.add(button1, 0, 1 ,1,1);
        root.add(pic1, 3, 0, 1, 1);
        root.add(button2, 3, 1, 1, 1);
        root.add(corridor, 2, 0, 1, 1);
        root.setHgap(30);
        root.setVgap(70);

        Scene preScene = new Scene(root, 350, 400);
        initialise.setScene(preScene);
        initialise.show();

        button1.setOnAction(event -> {
            initialise.close();
            Game game = new Game();
            this.GAME = game;
            Main.getPrimaryStage().setTitle("Snake Game");
            Scene scene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);
            Gui.chooseTheme(game);
            Main.getPrimaryStage().setScene(scene);
            Main.getPrimaryStage().show();
            game.start();
        });
    }
}
