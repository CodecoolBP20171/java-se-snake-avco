package com.codecool.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.Global;

public class Main extends Application {

    private static Stage primaryStage;

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
        primaryStage.setTitle("Snake Game");
        Scene scene = new Scene(game, Globals.WINDOW_WIDTH, Globals.WINDOW_HEIGHT);

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

        primaryStage.setScene(scene);
        primaryStage.show();

        game.start();
    }

}
