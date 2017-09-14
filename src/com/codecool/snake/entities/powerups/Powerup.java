package com.codecool.snake.entities.powerups;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeBody;
import com.codecool.snake.entities.snakes.SnakeHead;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.layout.Pane;

import java.util.*;

public abstract class Powerup extends GameEntity implements Interactable {

    private Queue<Vec2d> history = new LinkedList<>();

    public Powerup(Pane pane) {
        super(pane);
        pane.getChildren().add(this);

        Random rnd = new Random();
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);
    }

    @Override
    public abstract void apply(SnakeHead snakeHead);

    @Override
    public abstract String getMessage();

    public void setHistory(SnakeHead snakeHead) {
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

}
