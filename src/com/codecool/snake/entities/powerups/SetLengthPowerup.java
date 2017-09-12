package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

public class SetLengthPowerup extends Powerup {

    public SetLengthPowerup(Pane pane) {
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