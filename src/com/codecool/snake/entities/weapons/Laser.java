package com.codecool.snake.entities.weapons;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.AdvancedEnemy;
import com.codecool.snake.entities.enemies.NotSoSimpleEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

public class Laser extends GameEntity implements Animatable {

    private static final float speed = 3;
    private Point2D heading;
    private SnakeHead snake;

    public Laser(Pane pane, double x, double y, double dir, SnakeHead snake) {
        super(pane);
        this.snake = snake;
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
        setX(getX() + heading.getX() * speed);
        setY(getY() + heading.getY() * speed);

        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof SimpleEnemy) {
                    Globals.setNumOfEnemies(-1);
                    snake.setScore();
                    spawnEnemy();
                    entity.destroy();
                    System.out.println("Good shot!");
                    explodeEnemy(entity);
                    destroy();
                }
            }
        }
    }

    private void explodeEnemy(GameEntity entity) {
        ImageView explosion;
        if (entity instanceof AdvancedEnemy) {
            explosion = new ImageView(new Image("explosion_test_2.gif"));
            explosion.setFitWidth(100);
            explosion.setFitHeight(60);
            explosion.setX(entity.getX() - 20);
            explosion.setY(entity.getY() - 10);
        } else {
            explosion = new ImageView(new Image("explosion_test.gif"));
            explosion.setFitWidth(30);
            explosion.setFitHeight(30);
            explosion.setX(entity.getX() - 5);
            explosion.setY(entity.getY() - 5);
        }
        Globals.game.getChildren().add(explosion);
    }

    private void spawnEnemy() {
        Random rnd = new Random();
        int choose = rnd.nextInt(3) + 1;
        if (choose == 1) {
            new SimpleEnemy(pane);
        } else if (choose == 2) {
            new NotSoSimpleEnemy(pane);
        } else {
            new AdvancedEnemy(pane);
        }
    }
}
