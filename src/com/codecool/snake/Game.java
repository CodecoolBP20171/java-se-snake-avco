package com.codecool.snake;

import com.codecool.snake.entities.enemies.AdvancedEnemy;
import com.codecool.snake.entities.enemies.NotSoSimpleEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.AddHealthPowerup;
import com.codecool.snake.entities.powerups.SetLengthPowerup;
import com.codecool.snake.entities.powerups.SetTurnRatePowerup;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class Game extends Pane {

    public void start() {
        SnakeHead snake1 =  new SnakeHead(this, 100, 500, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, "red");
        SnakeHead snake2 =  new SnakeHead(this, 200, 500, KeyCode.A, KeyCode.D, KeyCode.W, "blue");


        addProgressBar();
        Globals.players.add(snake1);
        Globals.players.add(snake2);
        Globals.SimpleEnemies.add(Globals.simpleEnemy);
        Globals.SimpleEnemies.add(Globals.simpleEnemy1);
        Globals.SimpleEnemies.add(Globals.simpleEnemy2);
        Globals.SimpleEnemies.add(Globals.simpleEnemy3);

        Gui.createScoreBar();

        int numberOfPowerups = 1;
        for (int i = 0; i < numberOfPowerups ; i++) {
            new SetLengthPowerup(this);
            new AddHealthPowerup(this);
            new SetTurnRatePowerup(this);
        }
        new SimpleEnemy(this);
        new SimpleEnemy(this);
        new AdvancedEnemy(this);
        new NotSoSimpleEnemy(this);
        new NotSoSimpleEnemy(this);

        EventHandler oldKeyPressedHandler = this.getScene().getOnKeyPressed();
        this.getScene().setOnKeyPressed(event -> {
            if (oldKeyPressedHandler != null) {
                oldKeyPressedHandler.handle(event);
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                Gui.popUpWindow(Main.getPrimaryStage());
            }

        });

        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }
    private void addProgressBar(){
        int counter = 0;
        int x = 100;
        int y = 50;
        for (int i = 0; i < SnakeHead.healthBar.size(); i++) {
            if (counter <=2){
                x = (int) (Globals.WINDOW_WIDTH-x - 100);
            }   else if (counter == 4){

                x = (int) (Globals.WINDOW_WIDTH-x - 100);
                y = (int) (Globals.WINDOW_WIDTH-y - 50);
            }
            ProgressBar progressBar = SnakeHead.healthBar.get(i);
            progressBar.setLayoutX(x);
            progressBar.setLayoutY(y);
            this.getChildren().add(progressBar);
        }
    }
}
