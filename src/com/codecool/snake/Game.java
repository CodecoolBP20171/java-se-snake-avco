package com.codecool.snake;

import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Game extends Pane {


    public void start() {
        new SnakeHead(this, 300, 500, KeyCode.LEFT, KeyCode.RIGHT);
        new SnakeHead(this, 300, 500, KeyCode.A, KeyCode.D);

        new SimpleEnemy(this);
        new SimpleEnemy(this);
        new SimpleEnemy(this);
        new SimpleEnemy(this);

        new SimplePowerup(this);
        new SimplePowerup(this);
        new SimplePowerup(this);
        new SimplePowerup(this);
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }
}
