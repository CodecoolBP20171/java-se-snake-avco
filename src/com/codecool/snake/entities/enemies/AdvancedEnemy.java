package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

import java.util.Random;

public class AdvancedEnemy extends SimpleEnemy {

    private SnakeHead snake;

    public AdvancedEnemy(Pane pane) {
        super(pane);
        setImage(Globals.advancedEnemy);
        Random rnd = new Random();
        this.snake = Globals.players.get(rnd.nextInt(Globals.players.size()));
    }

    @Override
    public void step() {
        direction = Math.toDegrees(Math.atan2(snake.getY() - getY(), snake.getX() - getX()));
        direction += 90;
        setRotate(direction);
        javafx.geometry.Point2D heading = Utils.directionToVector(direction, speed);

        setX(getX() + heading.getX());
        setY(getY() + heading.getY());

        if (!Globals.players.contains(snake)) {
            if (!Globals.players.isEmpty()) {
                snake = Globals.players.get(0);
            } else {
                destroy();
            }
        }
    }

}
