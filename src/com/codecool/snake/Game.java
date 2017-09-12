package com.codecool.snake;

import com.codecool.snake.entities.enemies.AdvancedEnemy;
import com.codecool.snake.entities.enemies.NotSoSimpleEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.security.Key;

public class Game extends Pane {

    public void start() {
        createSnakes();
        createSimpleEnemy();
        createSimplePowerup();

        Globals.SimpleEnemies.add(Globals.simpleEnemy);
        Globals.SimpleEnemies.add(Globals.simpleEnemy1);
        Globals.SimpleEnemies.add(Globals.simpleEnemy2);
        Globals.SimpleEnemies.add(Globals.simpleEnemy3);
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }

    private void createSnakes() {
        new SnakeHead(this, 100, 500, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);
        new SnakeHead(this, 200, 500, KeyCode.A, KeyCode.D, KeyCode.W);
    }

    private void createSimpleEnemy() {
        new SimpleEnemy(this);
        new SimpleEnemy(this);
        new SimpleEnemy(this);
        new SimpleEnemy(this);
    }

        new SimpleEnemy(this, snake);
        new AdvancedEnemy(this, snake);
        new NotSoSimpleEnemy(this, snake);

    private void createSimplePowerup() {
        new SimplePowerup(this);
        new SimplePowerup(this);
        new SimplePowerup(this);
        new SimplePowerup(this);
    }
}
