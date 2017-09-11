package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class SetLongerPowerup extends GameEntity implements Interactable {

    // a simple powerup that makes the snake grow TODO make other powerups

    public SetLongerPowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupLonger);
        pane.getChildren().add(this);

        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        snakeHead.addPart(10);
        snakeHead.changeHealth(20);
        destroy();
    }

    @Override
    public String getMessage() {
        return "Got power-up 10 :)";
    }
}