package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

public class SetLengthPowerup extends Powerup {

    public SetLengthPowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupSetLength);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        int numberOfNewParts = 4;
        snakeHead.addPart(numberOfNewParts);
//        setHistory(snakeHead);
        destroy();
    }

    @Override
    public String getMessage() {
        return "You have new parts!";
    }
}