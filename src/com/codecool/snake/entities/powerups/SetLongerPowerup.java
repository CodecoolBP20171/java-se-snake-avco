package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeBody;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class SetLongerPowerup extends Powerup {

    public SetLongerPowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupLonger);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        snakeHead.addPart(2);
        destroy();
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "add new parts";
    }
}