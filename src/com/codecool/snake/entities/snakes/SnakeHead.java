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

import javafx.scene.image.Image;
import javafx.scene.control.ProgressBar;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.HashSet;

public class SnakeHead extends GameEntity implements Animatable, Interactable {

    private float speed = 2;
    private float maxSpeed = 4;
    private float turnRate = 2;
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

    private SimpleStringProperty score = new SimpleStringProperty("Score: 0");
    private int intScore;
    private static int progressBarPosition = 20;
    private static String progressBarColor = " -fx-accent: red; ";
    private ProgressBar progressBar = new ProgressBar(1);


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
        progressBar.setLayoutX(progressBarPosition);
        progressBar.setLayoutY(20);
        Main.getGAME().getChildren().add(progressBar);

        addPart(4);
        progressBarPosition = (int) Globals.WINDOW_WIDTH - health - 20;
        progressBar.setStyle("" +
                "-fx-control-inner-background: white;" +
                progressBarColor +
                "-fx-fill:null;" +
                "-fx-padding: 0 0 -16 0;"
        );
        progressBarColor = " -fx-accent: blue; ";
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

    public float getTurnRate() {
        return turnRate;
    }

    public float getMaxSpeed() {
        return maxSpeed;
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

    public String getSnakeHeadColor() {
        return snakeHeadColor;
    }

    public static void decrSnakesAlive(int decrement) {
        snakesAlive -= decrement;
        System.out.println(snakesAlive);
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
//                    System.out.println(interactable.getMessage());
                }
            }
        }
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
            gameOver();
        }
    }

    private void gameOver() {
        if (Globals.players.size() == 1) {
            System.out.println("Game Over");
            Gui.gameOverWindow(Main.getPrimaryStage(), length - 4);
        } else {
            Globals.scoreList.put(snakeHeadColor, length - 4);
            destroyAll(tail);
        }
        Globals.players.remove(this);
    }


    private void destroyAll(GameEntity tail) {
        GameEntity tailParent;
        if (tail instanceof SnakeBody) {
            SnakeBody newTail = (SnakeBody) tail;
            tailParent = newTail.getTailParent();
            newTail.destroy();
            destroyAll(tailParent);
        } else {
            tail.destroy();
            System.out.println("The " + snakeHeadColor + " snake is died");
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
            snakesAlive = 0;
            gameOver();
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
