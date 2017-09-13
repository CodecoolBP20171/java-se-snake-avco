package com.codecool.snake.entities.snakes;

import com.codecool.snake.Globals;
import com.codecool.snake.Gui;
import com.codecool.snake.Main;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import javafx.event.EventHandler;
import com.codecool.snake.entities.weapons.Laser;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class SnakeHead extends GameEntity implements Animatable {

    private static float speed = 2;
    private static float turnRate = 2;
    private static int snakesAlive;
    private GameEntity tail; // the last element. Needed to know where to add the next part.
    private int health;
    private long lastShotTime;
    private long reloadTime = 300;
    private boolean leftKeyDown = false;
    private boolean rightKeyDown = false;
    private boolean shoot = false;
    private double dir;
    private int timer;
    private int length;
    private String snakeHeadColor;
    private Image snakeHeadImage;

    public SnakeHead(Pane pane, int xc, int yc, KeyCode leftCode, KeyCode rightCode, KeyCode shootCode) {
        super(pane);
        tail = this;
        health = 100;
        setX(xc);
        setY(yc);

        setSnakeHeadImage();
        setImage(snakeHeadImage);

        pane.getChildren().add(this);
        initEventHandlers(pane, leftCode, rightCode, shootCode);
        snakesAlive++;

        addPart(4);
    }

    private void setSnakeHeadImage() {
        if (snakesAlive % 2 == 1) {
            snakeHeadColor = "green";
            snakeHeadImage = Globals.snakeHeadGreen;
        } else {
            snakeHeadColor = "red";
            snakeHeadImage = Globals.snakeHeadRed;
        }
    }

    public static float getTurnRate() {
        return turnRate;
    }

    public static void setTurnRate(float turnRate) {
        SnakeHead.turnRate = turnRate;
    }

    public static void setSpeed(float speed) {
        SnakeHead.speed = speed;
    }

    public static float getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public String getSnakeHeadColor() {
        return snakeHeadColor;
    }

    public void step() {
        dir = getRotate();
        if (leftKeyDown) {
            dir = dir - turnRate;
        }
        if (rightKeyDown) {
            dir = dir + turnRate;
        }
        // set rotation and position
        setRotate(dir);
        checkGate();
        Point2D heading = Utils.directionToVector(dir, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
        laserShoot(dir);
        checkTheCollided();

        //isGameOver();
    }

    public void checkGate() {
        if (timer > 0) {
            timer--;
        }
        double gateSize = Globals.WINDOW_HEIGHT / 2;
        if (getY() > gateSize - 50 && getY() < gateSize + 50) {
            if (getX() > Globals.WINDOW_WIDTH - 5 && timer == 0) {
                setX(10);
                dir += 180;
                timer = 60;
            } else if (getX() < 5 && timer == 0) {
                setX(Globals.WINDOW_WIDTH - 30);
                dir += 180;
                timer = 60;
            }
        } else {
            isGameOver();
        }
    }

    public boolean isReloaded() {
        long timeDelta = System.currentTimeMillis() - lastShotTime;
        return timeDelta > reloadTime;
    }

    private void laserShoot(double dir) {
        // laser shot
        if (shoot && isReloaded()) {
            new Laser(pane, getX(), getY(), dir);
            lastShotTime = System.currentTimeMillis();
            this.toFront();
        }
    }

    public void addPart(int numParts) {
        for (int i = 0; i < numParts; i++) {
            SnakeBody newPart = new SnakeBody(pane, tail);
            tail = newPart;
            length++;
        }
    }

    private void checkTheCollided() {
        // check if collided with an enemy or a powerup
        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof Interactable) {
                    Interactable interactable = (Interactable) entity;
                    interactable.apply(this);
                    System.out.println(interactable.getMessage());
                }
            }
        }
    }

    public void changeHealth(int diff) {
        health += diff;
    }

    private void isGameOver() {
        // check for game over condition
        if (isOutOfBounds() || health <= 0) {
            snakesAlive--;
            if (snakesAlive == 0) {
                System.out.println("Game Over");
                Gui.gameOverWindow(Main.getPrimaryStage(), length);
            } else {
                this.destroy();
            }
        }
    }

    private void initEventHandlers(Pane pane, KeyCode leftCode, KeyCode rightCode, KeyCode shootCode) {
        //EventHandler has to be chained to each other due to only one can be set
        EventHandler oldKeyPressedHandler = pane.getScene().getOnKeyPressed();
        pane.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (oldKeyPressedHandler != null) {
                    oldKeyPressedHandler.handle(event);
                }
                if (leftCode == event.getCode()) {
                    leftKeyDown = true;

                } else if (rightCode == event.getCode()) {
                    rightKeyDown = true;
                } else if (shootCode == event.getCode()) {
                    shoot = true;
                }
            }
        });

        EventHandler keyReleasedEventHandler = pane.getScene().getOnKeyReleased();
        pane.getScene().setOnKeyReleased(event -> {
            if (keyReleasedEventHandler != null) {
                keyReleasedEventHandler.handle(event);
            }
            if (leftCode == event.getCode()) {
                leftKeyDown = false;

            } else if (rightCode == event.getCode()) {
                rightKeyDown = false;

            } else if (shootCode == event.getCode()) {
                shoot = false;
            }
        });
    }
}
