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

    protected Point2D heading;
    protected static final int damage = 10;
    protected double direction;
    protected Random rnd = new Random();
    protected int charSize;
    protected int speed;

    public SimpleEnemy(Pane pane) {
        super(pane);
        setImage(Globals.SimpleEnemies.get(rnd.nextInt(Globals.SimpleEnemies.size())));
        pane.getChildren().add(this);
        this.setValidPosition();
        Globals.setNumOfEnemies(1);
    }

    public void setValidPosition() {
        speed = 1;
        charSize = 40;
        while (true) {
            double xPos = (double) rnd.nextInt((int) (Globals.WINDOW_WIDTH - charSize) - charSize + 1) + charSize;
            double yPos = (double) rnd.nextInt((int) (Globals.WINDOW_HEIGHT - charSize) - charSize + 1) + charSize;
            setX(xPos);
            setY(yPos);
            for (SnakeHead player : Globals.players) {
                if (getBoundsInParent().intersects(player.getBoundsInParent())) {
                    break;
                }
            }
            break;
        }
        direction = rnd.nextDouble() * 360;
        setRotate(direction);
        heading = Utils.directionToVector(direction, speed);
    }

    public void checkUnitNumbers() {
        if (Globals.getNumOfEnemies() < 5) {
            int choose = rnd.nextInt(3) + 1;
            switch (choose) {
                case 1: new SimpleEnemy(pane);
                        break;
                case 2: new NotSoSimpleEnemy(pane);
                        break;
                case 3: new AdvancedEnemy(pane);
                        break;
            }
        }
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
        checkUnitNumbers();
    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        destroy();
        Globals.setNumOfEnemies(-1);
    }

    @Override
    public String getMessage() {
        return "10 damage";
    }
}
