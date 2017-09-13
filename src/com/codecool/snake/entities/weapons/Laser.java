package com.codecool.snake.entities.weapons;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class Laser extends GameEntity implements Animatable {

    private static final float speed = 3;
    private Point2D heading;

    public Laser(Pane pane, double x, double y, double dir) {
        super(pane);
        setX(x);
        setY(y);
        setRotate(dir);
        setTranslateX(15);
        setImage(Globals.laser);
        pane.getChildren().add(this);
        heading = Utils.directionToVector(dir, speed);

    }

    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }

        // check collision with an enemy
        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof SimpleEnemy) {
                    Globals.setNumOfEnemies(-1);
                    ((SimpleEnemy) entity).checkUnitNumbers();
                    entity.destroy();
                    System.out.println("Good shot!");
                    destroy();
                }
            }
        }
        setX(getX() + heading.getX() * speed);
        setY(getY() + heading.getY() * speed);
    }
}
