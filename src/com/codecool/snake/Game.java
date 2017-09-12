package com.codecool.snake;

import com.codecool.snake.entities.enemies.AdvancedEnemy;
import com.codecool.snake.entities.enemies.NotSoSimpleEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerup;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;


public class Game extends Pane {

    public void start() {
        SnakeHead snake =  new SnakeHead(this, 100, 500, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);
        SnakeHead snake2 =  new SnakeHead(this, 200, 500, KeyCode.A, KeyCode.D, KeyCode.W);

        createSimplePowerup();

        Globals.SimpleEnemies.add(Globals.simpleEnemy);
        Globals.SimpleEnemies.add(Globals.simpleEnemy1);
        Globals.SimpleEnemies.add(Globals.simpleEnemy2);
        Globals.SimpleEnemies.add(Globals.simpleEnemy3);

        new SimpleEnemy(this, snake);
        new AdvancedEnemy(this, snake);
        new NotSoSimpleEnemy(this, snake);


        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }


    private void createSimplePowerup() {
        new SimplePowerup(this);
        new SimplePowerup(this);
        new SimplePowerup(this);
        new SimplePowerup(this);
    }
}
