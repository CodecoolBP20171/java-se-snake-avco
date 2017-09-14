package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.*;

public class SnakeBody extends GameEntity implements Animatable, Interactable {

    private GameEntity parent;
    private Queue<Vec2d> history = new LinkedList<>();
    private int historySize = 10;
    private List<GameEntity> snakeParts = new ArrayList<>();

    public SnakeBody(Pane pane, GameEntity parent) {
        super(pane);
        if (parent instanceof SnakeHead) {
            snakeParts.add(parent);
        } else {
            SnakeBody prevBody = (SnakeBody) parent;
            snakeParts.addAll(prevBody.getSnakeParts());
        }
        snakeParts.add(this);
        this.parent = parent;
        setImage(Globals.snakeBody);

        // place it visually below the current tail
        List<Node> children = pane.getChildren();
        children.add(children.indexOf(parent), this);

        double xc = parent.getX();
        double yc = parent.getY();
        setX(xc);
        setY(yc);
        for (int i = 0; i < historySize; i++) {
            history.add(new Vec2d(xc, yc));
        }
    }

    public List<GameEntity> getSnakeParts() {
        return snakeParts;
    }

    public void step() {
        Vec2d pos = history.poll(); // remove the oldest item from the history
        setX(pos.x);
        setY(pos.y);
        history.add(new Vec2d(parent.getX(), parent.getY())); // add the parent's current position to the beginning of the history
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        SnakeHead headOfThisBody = (SnakeHead) this.snakeParts.get(0);
        if (!headOfThisBody.equals(snakeHead)) {
            SnakeBody lastPart = (SnakeBody) snakeHead.getTail();
            Globals.players.remove(snakeHead);
            for (GameEntity pieceOfSnake: lastPart.getSnakeParts()) {
                pieceOfSnake.destroy();
            }
        }
    }

    @Override
    public String getMessage() {
        return null;
    }

    public Queue<Vec2d> getHistory() {
        return history;
    }

    public void pollHistory() {
        history.poll();
    }
}