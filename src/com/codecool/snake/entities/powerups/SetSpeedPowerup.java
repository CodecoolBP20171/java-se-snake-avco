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
//        snakeHead.changeHealth(20);
        speed += 2;
        snakeHead.setSpeed(speed);
        destroy();
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "add new parts";
    }
}