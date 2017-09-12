package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

public class AdvancedEnemy extends SimpleEnemy {

    public AdvancedEnemy(Pane pane, SnakeHead snake) {
        super(pane, snake);
        setImage(Globals.advancedEnemy);
    }

    @Override
    public void step() {
        direction = Math.toDegrees(Math.atan2(snake.getY() - getY(), snake.getX() - getX()));
        direction += 90;
        setRotate(direction);
        javafx.geometry.Point2D heading = Utils.directionToVector(direction, speed);

        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        destroy();
        new AdvancedEnemy(pane, snake);
    }
}
