package com.codecool.snake.entities.snakes;

import com.codecool.snake.Globals;
import com.codecool.snake.Gui;
import com.codecool.snake.Main;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import com.codecool.snake.entities.weapons.Laser;
import javafx.geometry.Point2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SnakeHead extends GameEntity implements Animatable,Interactable {

    private float speed = 2;
    private float maxSpeed = 4;
    private float turnRate = 2;
    private static int snakesAlive;
    private GameEntity tail; // the last element. Needed to know where to add the next part.
    private int timer;
    private int health;
    private int length;
    private int intScore;
    private double dir;
    private long lastShotTime;
    private long reloadTime = 300;
    private boolean shoot = false;
    private boolean leftKeyDown = false;
    private boolean rightKeyDown = false;
    private String snakeColor;
    private SimpleStringProperty score = new SimpleStringProperty("Score: 0");
    private ProgressBar progressBar = new ProgressBar(1);
    public static List<ProgressBar> healthBar = new ArrayList<>();

    public SnakeHead(Pane pane, int xc, int yc, KeyCode leftCode, KeyCode rightCode, KeyCode shootCode, String snakeColor) {
        super(pane);
        tail = this;
        health = 100;
        setX(xc);
        setY(yc);
        this.snakeColor = snakeColor;
        pane.getChildren().add(this);
        setImage(Globals.snakeHead);

        initEventHandlers(pane, leftCode, rightCode, shootCode);
        snakesAlive++;
        String color = String.format("-fx-accent: %s", snakeColor);
        progressBar.setStyle("" +
                "-fx-control-inner-background: white;" +
                color
        );
        healthBar.add(this.progressBar);
        addPart(4);
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getTurnRate() {
        return turnRate;
    }

    public void setTurnRate(float turnRate) {
        this.turnRate = turnRate;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
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
                setX(0);
                dir += 180;
                timer = 60;
            } else if (getX() < 5 && timer == 0) {
                setX(Globals.WINDOW_WIDTH);
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

    public HashSet<GameEntity> getSnakeParts() {
        return null;
    }
    public void changeHealth(int diff) {
        if (diff < 0) {
            health += diff;
        } else {

            if (health != 100 && health + diff < 100) {

                health += diff;
            } else {
                health = 100;
            }
        }
        Gui.createHealthBar(progressBar, health);
    }

    private void isGameOver() {
        // check for game over condition
        if (isOutOfBounds() || health <= 0) {
            System.out.println("Game Over");
            Globals.players.remove(this);
            snakesAlive--;
            if (snakesAlive == 0) {
                Gui.gameOverWindow(Main.getPrimaryStage(), length);
            } else {
                this.destroy();
            }
        }
    }

    public void setScore() {
        intScore++;
        score.setValue("Score: " + Integer.toString(intScore));
    }

    public SimpleStringProperty getScore() {
        return score;
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

    @Override
    public void apply(SnakeHead snakeHead) {
        if (this != snakeHead) {
            Globals.gameLoop.stop();
        }
    }

    @Override
    public String getMessage() {
        return null;
    }

    public GameEntity getTail() {
        return tail;
    }
}
