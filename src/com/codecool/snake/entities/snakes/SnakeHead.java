package com.codecool.snake.entities.snakes;

import com.codecool.snake.Globals;
import com.codecool.snake.Gui;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.powerups.AddHealthPowerup;
import com.codecool.snake.entities.powerups.Powerup;
import com.codecool.snake.entities.powerups.SetLengthPowerup;
import com.codecool.snake.entities.powerups.SetTurnRatePowerup;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import com.codecool.snake.entities.weapons.Laser;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.*;

public class SnakeHead extends GameEntity implements Animatable, Interactable {

    private float speed = 2;
    private float maxSpeed = 4;
    private float turnRate = 2;
    private GameEntity tail; // the last element. Needed to know where to add the next part.
    private int timer;
    private int health;
    private int intScore = -1;
    private double dir;
    private long lastShotTime;
    private long reloadTime = 300;
    private boolean shoot = false;
    private boolean leftKeyDown = false;
    private boolean rightKeyDown = false;
    public String name;
    private SimpleStringProperty score = new SimpleStringProperty();
    private ProgressBar progressBar = new ProgressBar(1);
    private String color;

    public static List<String> colors = new ArrayList<>();
    public static HashMap<String, Image> snakeHeadColor = new HashMap<>();
    public static List<ProgressBar> healthBar = new ArrayList<>();
    public static List<ArrayList<KeyCode>> snakeControls = new ArrayList<>();
    public static int usedSnakeControl = 0;

    private KeyCode leftCode;
    private KeyCode rightCode;
    private KeyCode shootCode;
    private long timeOfLastCreatedPowerups;

    public SnakeHead(Pane pane, int xc, int yc, int zc) {
        super(pane);
        tail = this;
        health = 100;

        setX(xc);
        setY(yc);
        setRotate(zc);

        pane.getChildren().add(this);

        ArrayList<KeyCode> snakeControl = snakeControls.get(usedSnakeControl);
        color = colors.get(usedSnakeControl);

        leftCode = snakeControl.get(0);
        rightCode = snakeControl.get(1);
        shootCode = snakeControl.get(2);
        initEventHandlers(pane, leftCode, rightCode, shootCode);

        setImage(snakeHeadColor.get(color));
        usedSnakeControl++;

        timeOfLastCreatedPowerups = System.currentTimeMillis();

        String snakeHeadColor = String.format("-fx-accent: %s", this.color);
        progressBar.setStyle("" +
                "-fx-control-inner-background: white;" +
                snakeHeadColor
        );
        healthBar.add(this.progressBar);
        addPart(4);
        this.name = color.toUpperCase();
    }

    public static void clearStatic() {
        colors.clear();
        snakeHeadColor.clear();
        healthBar.clear();
        snakeControls.clear();
        usedSnakeControl = 0;
    }

    public String getColor() {
        return color;
    }

    public static void snakeSettings() {
        ArrayList<KeyCode> keys = new ArrayList<>();
        keys.add(KeyCode.LEFT);
        keys.add(KeyCode.RIGHT);
        keys.add(KeyCode.UP);
        snakeControls.add(keys);
        keys = new ArrayList<>();
        keys.add(KeyCode.A);
        keys.add(KeyCode.D);
        keys.add(KeyCode.W);
        snakeControls.add(keys);
        keys = new ArrayList<>();
        keys.add(KeyCode.F);
        keys.add(KeyCode.H);
        keys.add(KeyCode.T);
        snakeControls.add(keys);
        keys = new ArrayList<>();
        keys.add(KeyCode.J);
        keys.add(KeyCode.L);
        keys.add(KeyCode.I);
        snakeControls.add(keys);

        colors.add("red");
        colors.add("green");
        colors.add("blue");
        colors.add("yellow");
        snakeHeadColor.put("red", new Image("snake_head_red.png"));
        snakeHeadColor.put("green", new Image("snake_head_green.png"));
        snakeHeadColor.put("blue", new Image("snake_head_blue.png"));
        snakeHeadColor.put("yellow", new Image("snake_head_yellow.png"));
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
        addPowerUpsRandomly();

        Globals.scoreList.put(color, intScore);
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
            new Laser(pane, getX(), getY(), dir, this);
            lastShotTime = System.currentTimeMillis();
            this.toFront();
        }
    }

    public void addPart(int numParts) {
        for (int i = 0; i < numParts; i++) {
            SnakeBody newPart = new SnakeBody(pane, tail);
            tail = newPart;
        }
    }

    private void checkTheCollided() {
        // check if collided with an enemy or a powerup
        for (GameEntity entity : Globals.getGameObjects()) {
            if (getBoundsInParent().intersects(entity.getBoundsInParent())) {
                if (entity instanceof Interactable) {
                    Interactable interactable = (Interactable) entity;
                    interactable.apply(this);
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
            gameOver("normal");
        }
    }

    private void gameOver(String endType) {
        if (Globals.players.size() == 1 ||
                (endType.equals("tie"))) {
            System.out.println("Game Over");
            Gui.gameOverWindow();
        } else {
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
            Gui.explodeSnake(this);
            tail.destroy();
            System.out.println("The " + color + " snake is died");
        }
    }

    public void setScore() {
        intScore++;
        score.setValue(name + ": " + Integer.toString(intScore));
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
        if (!this.equals(snakeHead)) {
            if (Globals.players.size() < 3) {
                gameOver("tie");
                System.out.println("Tie");
            } else {
                destroyAll(tail);
                Globals.players.remove(this);
            }
        }
    }

    @Override
    public String getMessage() {
        return null;
    }

    public GameEntity getTail() {
        return tail;
    }

    public void addPowerUpsRandomly() {
        long currentTime = System.currentTimeMillis();
        Random random = new Random();
        int minTime = 1000;
        int maxTime = 2000;
        int randomTimeToCreatePowerups = random.nextInt(maxTime) + minTime;

        if ((timeOfLastCreatedPowerups + randomTimeToCreatePowerups) < currentTime) {
            int maxNumberOfPowerups = 2;
            int numberOfNewPowerUps = random.nextInt(maxNumberOfPowerups) + 1;
            int numberOfPowerupTypes = 3;
            int option;
            timeOfLastCreatedPowerups = System.currentTimeMillis();

            if (Powerup.getNumberOfPowerups() < 3) {
                for (int i = 0; i < numberOfNewPowerUps; i++) {
                    option = random.nextInt(numberOfPowerupTypes);

                    switch (option) {
                        case 0:
                            new AddHealthPowerup(pane);
                            break;
                        case 1:
                            new SetLengthPowerup(pane);
                            break;
                        case 2:
                            new SetTurnRatePowerup(pane);
                            break;
                    }
                }
            }
        }
    }
}
