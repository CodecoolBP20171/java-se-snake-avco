package com.codecool.snake;

import com.codecool.snake.entities.enemies.AdvancedEnemy;
import com.codecool.snake.entities.enemies.NotSoSimpleEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.AddHealthPowerup;
import com.codecool.snake.entities.powerups.SetLengthPowerup;
import com.codecool.snake.entities.powerups.SetTurnRatePowerup;
import com.codecool.snake.entities.snakes.SnakeBody;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class Game extends Pane {

    private int numberOfPlayers = 0;

    public Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void start() {
        SnakeHead.snakeSettings();
        SnakeBody.setSnakeBodySetup();
        createSnakes(numberOfPlayers);

        Globals.SimpleEnemies.add(Globals.simpleEnemy);
        Globals.SimpleEnemies.add(Globals.simpleEnemy1);
        Globals.SimpleEnemies.add(Globals.simpleEnemy2);
        Globals.SimpleEnemies.add(Globals.simpleEnemy3);

        int numberOfPowerups = 1;
        for (int i = 0; i < numberOfPowerups; i++) {
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
                Gui.popUpWindow();
            }
        });

        addScoreBard();
        addProgressBar();
        Globals.gameLoop = new GameLoop();
        Globals.gameLoop.start();
    }

    private void createSnakes(int numberOfSnakes) {
        int x = 100;
        int y = 100;
        int z = 0;
        for (int i = 0; i < numberOfSnakes; i++) {
            switch (i){
                case 0 : x = 100; y = 100; z = 180; break;
                case 1 : x = 800; y = 100; z = 180; break;
                case 2 : x = 100; y = 500; z = 0; break;
                case 3 : x = 800; y = 500; z = 0; break;
            }
            Globals.players.add(new SnakeHead(this, x, y,z));
        }
    }

    private void addProgressBar(){
        for (int i = 0; i < SnakeHead.healthBar.size(); i++) {
            ProgressBar progressBar = SnakeHead.healthBar.get(i);
            switch (i){
                case 0 :
                    progressBar.setLayoutX(50);progressBar.setLayoutY(20);
                    break;
                case 1 :
                    progressBar.setLayoutX(Globals.WINDOW_WIDTH-150);progressBar.setLayoutY(20);
                    break;
                case 2 :
                    progressBar.setLayoutX(50);progressBar.setLayoutY(Globals.WINDOW_HEIGHT-40);
                    break;
                case 3 :
                    progressBar.setLayoutX(Globals.WINDOW_WIDTH-150);progressBar.setLayoutY(Globals.WINDOW_HEIGHT-40);
                    break;
            }
            this.getChildren().add(progressBar);
        }
    }

    private void addScoreBard(){
        Gui.createScoreBar(this, numberOfPlayers);
    }
}
