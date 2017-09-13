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

public class SetTurnRatePowerup extends Powerup {

    private float turnRate;
    private float speed;
    private Queue<Vec2d> history = new LinkedList<>();

    public SetTurnRatePowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupSetTurnRate);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        this.turnRate = snakeHead.getTurnRate();
        turnRate++;
        snakeHead.setTurnRate(turnRate);

        this.speed = snakeHead.getSpeed();
        speed += 1;

        if (speed < snakeHead.getMaxSpeed()) {
            snakeHead.setSpeed(speed);
            setHistory(snakeHead);
        }

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
        return "You can turn faster! Current turn rate is: " + turnRate;
    }
}
