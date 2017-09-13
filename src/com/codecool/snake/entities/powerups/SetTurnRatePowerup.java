package com.codecool.snake.entities.powerups;

import com.codecool.snake.Globals;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.scene.layout.Pane;

public class SetTurnRatePowerup extends Powerup {

    private float turnRate;

    public SetTurnRatePowerup(Pane pane) {
        super(pane);
        setImage(Globals.powerupSetTurnRate);
    }

    @Override
    public void apply(SnakeHead snakeHead) {
        this.turnRate = snakeHead.getTurnRate();
        turnRate++;
        snakeHead.setTurnRate(turnRate);
        destroy();
    }

    @Override
    public String getMessage() {
        return "You can turn faster! Current turn rate is: " + turnRate;
    }
}
