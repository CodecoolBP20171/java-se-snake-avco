package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import javafx.scene.layout.Pane;

public class NotSoSimpleEnemy extends SimpleEnemy {

    public NotSoSimpleEnemy(Pane pane) {
        super(pane);
        setImage(Globals.circlingENemy);
    }

    @Override
    public void step() {
        direction += 0.6;
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
        checkUnitNumbers();
    }
}
