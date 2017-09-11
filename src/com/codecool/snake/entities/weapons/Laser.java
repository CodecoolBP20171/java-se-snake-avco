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
    private static final long reloadTime = 400;
    private static long lastShotTime;
    private Point2D heading;

    public Laser(Pane pane, double x, double y, double dir) {
        super(pane);
        setImage(Globals.laser);
        pane.getChildren().add(this);
        setRotate(dir);
        heading = Utils.directionToVector(dir, speed);
        setX(x);
        setY(y);
        setTranslateX(15);
        lastShotTime = System.currentTimeMillis();
    }

    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }

        // check collision with an enemy
        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof SimpleEnemy) {
                    entity.destroy();
                    System.out.println("Good shot!");
                    destroy();
                }
            }
        }
        setX(getX() + heading.getX() * speed);
        setY(getY() + heading.getY() * speed);
    }

    public static boolean isReloaded() {
        long timeDelta = System.currentTimeMillis() - Laser.lastShotTime;
        return timeDelta > reloadTime;
    }
}
