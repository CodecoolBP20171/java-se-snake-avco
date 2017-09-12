package com.codecool.snake.entities.enemies;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.util.Random;

// a simple enemy TODO make better ones.
public class SimpleEnemy extends GameEntity implements Animatable, Interactable {

    private Point2D heading;
    private static final int damage = 10;
    private double direction;
    private Random rnd = new Random();

    public SimpleEnemy(Pane pane) {
        super(pane);


        setImage(Globals.SimpleEnemies.get(rnd.nextInt(Globals.SimpleEnemies.size())));
        pane.getChildren().add(this);
        int speed = 1;
        int charSize = 30;
        setX((double) rnd.nextInt((int) (Globals.WINDOW_WIDTH - charSize) - charSize + 1) + charSize);
        setY((double) rnd.nextInt((int) (Globals.WINDOW_HEIGHT - charSize) - charSize + 1) + charSize);

        direction = rnd.nextDouble() * 360;
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            direction += rnd.nextInt(41) + 160;
            setRotate(direction);
            heading = Utils.directionToVector(direction, 1);
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        destroy();
        new SimpleEnemy(pane);
    }

    @Override
    public String getMessage() {
        return "10 damage";
    }
}
