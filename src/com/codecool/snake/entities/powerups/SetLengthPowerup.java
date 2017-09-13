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

    private Queue<Vec2d> history = new LinkedList<>();

    public SetLengthPowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupSetLength);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        int numberOfNewParts = 4;
        snakeHead.addPart(numberOfNewParts);
        setHistory(snakeHead);
        destroy();
    }

    private void setHistory(SnakeHead snakeHead) {
        SnakeBody tailOfSnake = (SnakeBody) snakeHead.getTail();
        tailOfSnake.getSnakeParts().remove(snakeHead);
        List<SnakeBody> snakeFullBodyWithoutHead = new ArrayList<>();

        for (GameEntity part: tailOfSnake.getSnakeParts()) {
            snakeFullBodyWithoutHead.add((SnakeBody) part);
        }

        for (SnakeBody body: snakeFullBodyWithoutHead) {
            this.history = body.getHistory();
            for (int i = 0; i < history.size() - 5; i++) {
                body.pollHistory();
            }
        }
        tailOfSnake.getSnakeParts().add(0, snakeHead);
    }

    @Override
    public String getMessage() {
        return "You have new parts!";
    }
}