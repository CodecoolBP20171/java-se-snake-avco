package com.codecool.snake;

import com.codecool.snake.entities.enemies.AdvancedEnemy;
import com.codecool.snake.entities.enemies.NotSoSimpleEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.AddHealthPowerup;
import com.codecool.snake.entities.powerups.SetLengthPowerup;
import com.codecool.snake.entities.powerups.SetTurnRatePowerup;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.awt.*;

public class Game extends Pane {

    public void start() {
        SnakeHead snake =  new SnakeHead(this, 300, 500, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);
        SnakeHead snake2 =  new SnakeHead(this, 100, 500, KeyCode.A, KeyCode.D, KeyCode.W);

        Globals.players.add(snake);
        Globals.players.add(snake2);

        Globals.SimpleEnemies.add(Globals.simpleEnemy);
        Globals.SimpleEnemies.add(Globals.simpleEnemy1);
        Globals.SimpleEnemies.add(Globals.simpleEnemy2);
        Globals.SimpleEnemies.add(Globals.simpleEnemy3);

        int numberOfPowerups = 4;
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
