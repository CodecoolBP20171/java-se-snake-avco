package com.codecool.snake.entities.powerups;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeBody;
import com.codecool.snake.entities.snakes.SnakeHead;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.layout.Pane;

import java.util.*;

public abstract class Powerup extends GameEntity implements Interactable {

    private Queue<Vec2d> history = new LinkedList<>();
    private static int numberOfPowerups;
    private int entitySize;
    protected Random rand = new Random();

    public Powerup(Pane pane) {
        super(pane);
        pane.getChildren().add(this);
        setValidPosition();
        numberOfPowerups++;
    }

    public void setValidPosition() {
        entitySize = 40;
        boolean inValidPosition = true;
        while (inValidPosition) {
            double xPos = (double) rand.nextInt((int) (Globals.WINDOW_WIDTH - entitySize) - entitySize + 1) + entitySize;
            double yPos = (double) rand.nextInt((int) (Globals.WINDOW_HEIGHT - entitySize) - entitySize + 1) + entitySize;
            setX(xPos);
            setY(yPos);
            for (SnakeHead player: Globals.players) {
                if (getBoundsInParent().intersects(player.getBoundsInParent())) {
                    inValidPosition = false;
                }
            }
            break;
        }
    }

    @Override
    public abstract void apply(SnakeHead snakeHead);

    @Override
    public abstract String getMessage();

    public static void decreaseNumberOfPowerups() {
        Powerup.numberOfPowerups--;
    }

    public static int getNumberOfPowerups() {
        return numberOfPowerups;
    }

    public void setHistory(SnakeHead snakeHead) {
        SnakeBody tailOfSnake = (SnakeBody) snakeHead.getTail();
        tailOfSnake.getSnakeParts().remove(snakeHead);
        List<SnakeBody> snakeFullBodyWithoutHead = new ArrayList<>();

        for (GameEntity part: tailOfSnake.getSnakeParts()) {
            snakeFullBodyWithoutHead.add((SnakeBody) part);
        }

        for (SnakeBody body: snakeFullBodyWithoutHead) {
            this.history = body.getHistory();
            for (int i = 0; i < history.size() - 5; i++) {
                body.pollHistory();
            }
        }
        tailOfSnake.getSnakeParts().add(0, snakeHead);
    }


    public void increaseScore(SnakeHead snake) {
        snake.setScore();
    }


}
