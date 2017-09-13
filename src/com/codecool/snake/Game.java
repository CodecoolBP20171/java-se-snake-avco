package com.codecool.snake;

import com.codecool.snake.entities.enemies.AdvancedEnemy;
import com.codecool.snake.entities.enemies.NotSoSimpleEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.AddHealthPowerup;
import com.codecool.snake.entities.powerups.SetLengthPowerup;
import com.codecool.snake.entities.powerups.SetTurnRatePowerup;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.awt.*;

public class Game extends Pane {

    public void start() {
        SnakeHead snake1 =  new SnakeHead(this, 100, 500, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);
        SnakeHead snake2 =  new SnakeHead(this, 200, 500, KeyCode.A, KeyCode.D, KeyCode.W);

        Globals.players.add(snake1);
        Globals.players.add(snake2);

        Globals.SimpleEnemies.add(Globals.simpleEnemy);
        Globals.SimpleEnemies.add(Globals.simpleEnemy1);
        Globals.SimpleEnemies.add(Globals.simpleEnemy2);
        Globals.SimpleEnemies.add(Globals.simpleEnemy3);


        javafx.scene.control.Label score = new javafx.scene.control.Label();
        score.textProperty().bind(snake1.getScore());
        score.setLayoutX(30);
        score.setLayoutY(20);
        score.setFont(new javafx.scene.text.Font(24));
        this.getChildren().add(score);

        javafx.scene.control.Label score2 = new Label();
        score2.textProperty().bind(snake2.getScore());
        score2.setLayoutX(Globals.WINDOW_WIDTH - 130);
        score2.setLayoutY(20);
        score2.setFont(new Font(24));
        this.getChildren().add(score2);



        int numberOfPowerups = 1;
        for (int i = 0; i < numberOfPowerups ; i++) {
            new SetLengthPowerup(this);
            new AddHealthPowerup(this);
            new SetTurnRatePowerup(this);
        }
        new SimpleEnemy(this);
        new SimpleEnemy(this);
        new AdvancedEnemy(this);
        new NotSoSimpleEnemy(this);
        new NotSoSimpleEnemy(this);

        EventHandler oldKeyPressedHandler = this.getScene().getOnKeyPressed();
        this.getScene().setOnKeyPressed(event -> {
            if (oldKeyPressedHandler != null) {
                oldKeyPressedHandler.handle(event);
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                Gui.popUpWindow(Main.getPrimaryStage());
            }

        });


        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }
}
