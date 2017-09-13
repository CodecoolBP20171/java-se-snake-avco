package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SnakeBody extends GameEntity implements Animatable {

    private GameEntity parent;
    private Queue<Vec2d> history = new LinkedList<>();
    private static final int historySize = 10;
    private Image bodyImage;

    public SnakeBody(Pane pane, GameEntity parent) {
        super(pane);
        this.parent = parent;

        setBodyColor(parent);
        setImage(bodyImage);

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

    private void setBodyColor(GameEntity parent) {
        if (parent instanceof SnakeHead) {
            SnakeHead head = (SnakeHead) parent;
            if (head.getSnakeHeadColor().equals("green")) {
                bodyImage = Globals.snakeBodyGreen;
            } else {
                bodyImage = Globals.snakeBodyRed;
            }
        } else {
            SnakeBody bodyPart = (SnakeBody) parent;
            bodyImage = bodyPart.getBodyImage();
        }
    }

    public void step() {
        Vec2d pos = history.poll(); // remove the oldest item from the history
        setX(pos.x);
        setY(pos.y);
        history.add(new Vec2d(parent.getX(), parent.getY())); // add the parent's current position to the beginning of the history
    }

    public Image getBodyImage() {
        return bodyImage;
    }

    public GameEntity getTailParent() {
        return parent;
    }
}
