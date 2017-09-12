package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

public class AdvancedEnemy extends GameEntity implements Animatable, Interactable {

    private SnakeHead snake;
    private int damage = 10;

    public AdvancedEnemy(Pane pane, SnakeHead snake) {
        super(pane);
        this.snake = snake;
        setImage(Globals.simpleEnemy);
        pane.getChildren().add(this);
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }
        // Advanced enemy skill - following the player - SnakeHead object is needed.
        double direction = Math.toDegrees(Math.atan2(snake.getY() - getY(), snake.getX() - getX()));
        direction += 90;
        javafx.geometry.Point2D heading = Utils.directionToVector(direction, 1);

        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        destroy();
    }

    @Override
    public String getMessage() {
        return "10 damage";
    }
}
