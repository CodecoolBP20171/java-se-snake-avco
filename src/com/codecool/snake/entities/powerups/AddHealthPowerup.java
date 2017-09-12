package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

public class AddHealthPowerup extends Powerup{

    public AddHealthPowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupAddHealth);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        snakeHead.changeHealth(10);
        destroy();
    }

    @Override
    public String getMessage() {
        return "Health package!";
    }
}
