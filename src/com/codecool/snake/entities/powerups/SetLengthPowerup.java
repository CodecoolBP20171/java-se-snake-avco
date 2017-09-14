package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.snakes.SnakeBody;
import com.codecool.snake.entities.snakes.SnakeHead;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SetLengthPowerup extends Powerup {

    public SetLengthPowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupSetLength);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        increaseScore(snakeHead);
        int numberOfNewParts = 4;
        snakeHead.addPart(numberOfNewParts);
        setHistory(snakeHead);
        destroy();
    }

    @Override
    public String getMessage() {
        return "You have new parts!";
    }
}