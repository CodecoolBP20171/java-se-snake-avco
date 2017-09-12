package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

public class SetSpeedPowerup extends Powerup {

    private static float speed = SnakeHead.getSpeed();

    public SetSpeedPowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupBerry);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        speed++;
        snakeHead.setSpeed(speed);
        destroy();
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "set speed up";
    }
}